#!/usr/bin/env python

import nxt.locator
import time
import sys

try:
 sys.stdout.write("ready\n")
 sys.stdout.flush()
 while True:
  brick_name = sys.stdin.readline().rstrip()
  if brick_name:
   break
 sys.stdout.write("connecting\n")
 sys.stdout.flush()
 brick = nxt.locator.find_one_brick(host=None, name=brick_name, silent=True, strict=None, debug=False, method=nxt.locator.Method(bluetooth=True, fantomusb=False, fantombt=False))
 sys.stdout.write("success\n")
 sys.stdout.flush()

 while(1):
  mailbox = int(input())
  if mailbox>9 and mailbox<20:
   try:
    message = brick.message_read(mailbox, mailbox-10, True)
    sys.stdout.write(str(message[1])+"\n")
    sys.stdout.flush()
   except nxt.error.DirProtError, e:
    sys.stdout.write("nomessage\n")
    sys.stdout.flush()
  elif mailbox<10 and mailbox>-1:
   try:
    information = int(input())
    brick.message_write(mailbox, '%d' % information)
    sys.stdout.write("success\n")
    sys.stdout.flush()
   except nxt.error.DirProtError, e:
    sys.stdout.write("writeproblem\n")
    sys.stdout.flush()
  elif mailbox>19 and mailbox<21:
   try:
    brick.stop_program()
    sys.stdout.write("success\n")
    sys.stdout.flush()
   except nxt.error.DirProtError, e:
    sys.stdout.write("success\n")
    sys.stdout.flush()
  elif mailbox>20 and mailbox<22:
    brick.start_program("main.rxe")
    sys.stdout.write("success\n")
    sys.stdout.flush()
except nxt.locator.BrickNotFoundError, e:
 sys.stdout.write("fatal\n")
 sys.stdout.flush()

