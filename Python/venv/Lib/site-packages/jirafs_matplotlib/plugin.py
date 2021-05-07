import os
import subprocess
import tempfile
from typing import Optional

from jirafs.plugin import ImageMacroPlugin, PluginOperationError
from jirafs.types import JirafsMacroAttributes


class MatplotlibMixin(object):
    def _build_output(
        self, data: str, python_bin: Optional[str] = None,
    ):
        with tempfile.NamedTemporaryFile(
            dir=self.ticketfolder.path, suffix=".py"
        ) as outf:
            outf.write(data.encode("utf-8"))

            config = self.get_configuration()
            config_python_bin = config.get("python_bin", "python")

            python_bin = python_bin or config_python_bin

            env = os.environ
            env["JIRAFS_RENDER"] = "1"

            outf.flush()
            proc = subprocess.Popen(
                [python_bin, outf.name],
                stdin=subprocess.PIPE,
                stdout=subprocess.PIPE,
                stderr=subprocess.PIPE,
                env=env,
            )
            stdout, stderr = proc.communicate()

        if proc.returncode:
            raise PluginOperationError(
                "%s encountered an error while rendering matplotlib graph: %s"
                % (self.entrypoint_name, stderr.decode("utf-8"),)
            )
        if not len(stdout):
            raise PluginOperationError(
                "Rendered matplotlib graph did not return output on stdout; "
                "make sure you're rendering your graph with "
                "`matplotlib.pyplot.savefig(sys.stdout)`"
            )

        return stdout


class Matplotlib(MatplotlibMixin, ImageMacroPlugin):
    """ Render matplotlib charts in your Jira issues."""

    MIN_VERSION = "2.0.0"
    MAX_VERSION = "3.0.0"
    TAG_NAME = "matplotlib"

    def get_extension_and_image_data(self, data: str, attrs: JirafsMacroAttributes):
        python_bin = attrs.get("python", "python")

        return "png", self._build_output(data, python_bin=python_bin,)
