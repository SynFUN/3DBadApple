# -*- coding:936 -*-
import mcpi.block as block
import mcpi.minecraft as minecraft
import time

mc = minecraft.Minecraft.create()
while True:
    time.sleep(1)
    pos = mc.player.getTilePos()
    mc.postToChat(" x = " + str(pos.x) + "  y = " + str(pos.y) + "  z = " + str(pos.z))


