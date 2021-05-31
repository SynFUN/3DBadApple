from nbt import nbt

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

def testNbt():
    nbtfile = nbt.NBTFile()
    nbtfile.name = "mynbt.mca"
    nbtfile.tags.append(nbt.TAG_Int(name="DataVersion", value=1343))
    chunk0_0 = nbt.TAG_Compound(name="Chunk [0, 0]        in world at (0,0)")
    chunk0_0.tags.append(nbt.TAG_Int(name="DataVersion", value=1343))
    # chunk0_0.tags.append(nbt.TAG_Compound(name="ForgeDataVersion").tags.append(nbt.TAG_Int(name="minecraft", value=1343)))
    level = nbt.TAG_Compound(name="Level")
    entities = nbt.TAG_Compound()
    level.tags.append(entities)
    chunk0_0.tags.append(level)
    nbtfile.tags.append(chunk0_0)
    nbtfile.write_file("mynbt.mca")

if __name__ == "__main__":
    # testNbt()
    main()

