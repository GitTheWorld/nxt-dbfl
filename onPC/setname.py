#!/usr/bin/env python

import nxt.locator
import time
import sys

brick = nxt.locator.find_one_brick(host=None, name=None, silent=True, strict=None, debug=False, method=nxt.locator.Method(bluetooth=False, fantomusb=True, fantombt=False))
sys.stdout.write("ready\n")
sys.stdout.flush()
while True:
 brick_name = sys.stdin.readline().rstrip()
 if brick_name:
  break
brick.set_brick_name(brick_name);


