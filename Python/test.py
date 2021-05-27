def main():
    f = open("r.0.0.mca", "rb")
    i = 0
    while 1:
        c = f.read(1)
        i = i + 1
        if not c:
            break
        if i % 32 == 0:
            print()
        else:
            if ord(c) <= 15:
                print("0x0" + hex(ord(c))[2:] + " ", end="")
            else:
                print(hex(ord(c)) + " ", end="")
    f.close()


if __name__ == "__main__":
    main()

