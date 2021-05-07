import time
import threading
import logging
from matplotlib.figure import Figure

from io import BytesIO
from queue import Queue

from flask import Flask, Response, render_template
# from stream import Stream


########################################################################
class StreamEvent:
    """An Event-like class that signals all active clients when a new frame is
    available.
    """

    #----------------------------------------------------------------------
    def __init__(self):
        self.events = {}
        logging.debug(f'Instantiate {self!r}')

    #----------------------------------------------------------------------
    def wait(self):
        """Invoked from each client's thread to wait for the next frame."""
        logging.debug('Waiting...')
        ident = threading.get_ident()
        if ident not in self.events:
            # this is a new client
            # add an entry for it in the self.events dict
            # each entry has two elements, a threading.Event() and a timestamp
            self.events[ident] = [threading.Event(), time.time()]
        return self.events[ident][0].wait()

    #----------------------------------------------------------------------
    def set(self):
        """Invoked by the stream thread when a new frame is available."""

        logging.debug('Setting...')

        now = time.time()
        to_remove = []
        for ident, event in self.events.items():
            if not event[0].isSet():
                # if this client's event is not set, then set it
                # also update the last set timestamp to now
                event[0].set()
                event[1] = now
            else:
                # if the client's event is already set, it means the client
                # did not process a previous frame
                # if the event stays set for more than 5 seconds, then assume
                # the client is gone and remove it
                if now - event[1] > 5:
                    to_remove.append(ident)

        for remove in to_remove:
            del self.events[remove]

    #----------------------------------------------------------------------
    def clear(self):
        """Invoked from each client's thread after a frame was processed."""
        logging.debug('Clearing...')
        self.events[threading.get_ident()][0].clear()


########################################################################
class FigureStream(Figure):

    __class_attr = {
        'thread': None,  # background thread that reads frames from stream
        'frame': None,  # current frame is stored here by background thread
        'last_access': 0,  # time of last client access to the source
        'event': StreamEvent()}

    #----------------------------------------------------------------------
    def __init__(self, *args, **kwargs):
        """Start the background stream thread if it isn't running yet."""
        # logging.debug(f'Instantiate {self!r}')
        super().__init__(*args, **kwargs)
        self.__output = BytesIO()
        self.__buffer = Queue(maxsize=60)


        app = Flask(__name__)
        app.add_url_rule('/', view_func=self.__video_feed)
        threading.Thread(target=app.run, kwargs={'host': '0.0.0.0', 'port': '5005', 'threaded': True}).start()




    #----------------------------------------------------------------------
    def __get_frames(self):
        """Return the current stream frame."""

        while True:
            self.__class_attr['last_access'] = time.time()
            self.__class_attr['event'].wait()
            self.__class_attr['event'].clear()
            frame = self.__class_attr['frame']
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')



    # #----------------------------------------------------------------------
    # @staticmethod
    # def frames():
        # """"Generator that returns frames from the stream."""
        # raise RuntimeError('Must be implemented by subclasses.')

    #----------------------------------------------------------------------
    # @classmethod
    def _thread(self):
        """stream background thread."""
        logging.info('Starting stream thread.')
        # frames_iterator = self.frames()
        # for frame in frames_iterator:
        while True:

            try:
                frame = self.__buffer.get(timeout=5)
            except:
                break

            self.__class_attr['frame'] = frame
            self.__class_attr['event'].set()  # send signal to clients
            time.sleep(0)

            # if there hasn't been any clients asking for frames in
            # the last 10 seconds then stop the thread
            if time.time() - self.__class_attr['last_access'] > 10:
                # frames_iterator.close()
                logging.info('Stopping stream thread due to inactivity.')
                break
        self.__class_attr['thread'] = None


    #----------------------------------------------------------------------
    def __video_feed(self):
        """"""

        if self.__class_attr['thread'] is None:
            self.__class_attr['last_access'] = time.time()

            # start background frame thread
            self.__class_attr['thread'] = threading.Thread(target=self._thread)
            self.__class_attr['thread'].start()

            # wait until frames are available
            while self.__get_frames() is None:
                time.sleep(0)


        # stream = Stream()
        return Response(self.__get_frames(), mimetype='multipart/x-mixed-replace; boundary=frame')


    #----------------------------------------------------------------------
    def clients(self):
        """"""
        return len(self.__class_attr['event'].events)

    #----------------------------------------------------------------------
    def buffer_size(self):
        """"""
        return self.__buffer.qsize()


    #----------------------------------------------------------------------
    def feed(self):
        """"""
        self.__output.truncate(0)
        self.__output.seek(0)
        self.canvas.print_figure(self.__output, format='jpeg')

        return self.__buffer.put(self.__output.getvalue())
