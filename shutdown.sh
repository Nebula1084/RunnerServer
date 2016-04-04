#!/usr/bin/env bash
kill -9 `ps -ef | grep runner | sed -n '1p' | awk '{print $2}'`