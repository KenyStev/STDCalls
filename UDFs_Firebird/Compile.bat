@echo off

SET MSBUILD="C:\Program Files (x86)\MSBuild\14.0\Bin\MSBuild.exe"
REM SET RSVARS="C:\Program Files (x86)\Borland\Delphi7\Bin\idl2pas.bat"
REM SET RSVARS="C:\Program Files (x86)\Embarcadero\Studio\16.0\bin\rsvars.bat"

if /i %1%==test (
  SET PROJECT=TestProject\UnitTests.dproj
) else (
  SET PROJECT=Project3.dproj
) 

REM call %RSVARS%   
%MSBUILD% %PROJECT% "/t:Clean,Make" "/p:config=Debug" "/verbosity:minimal"

if %ERRORLEVEL% NEQ 0 GOTO END

echo. 
if /i %1%==test (
  TestProject\UnitTests.exe
)
:END