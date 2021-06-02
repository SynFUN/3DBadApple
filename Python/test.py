from mcpi.minecraft import Minecraft
from mcpi import block
from mcpi import event
from time import sleep

mc = Minecraft.create()
mc.setBlocks(0, 0, 0, 5, 5, 5, block.GOLD_BLOCK.data)
mc.setBlocks(0, 0, 0, 5, 5, 5, block.GOLD_BLOCK.id)
mc.player.setPos(5, 5, 6)
mc.postToChat("wdnmd")

