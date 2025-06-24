@echo off
@git add .
@set /p VERSION=Version:
@git commit -m %VERSION%
@git push -u origin master