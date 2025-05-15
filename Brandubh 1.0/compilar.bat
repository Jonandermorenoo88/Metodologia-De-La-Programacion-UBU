if not exist "bin" mkdir bin

javac -classpath .\bin;.\lib\* ^
-encoding UTF-8 ^
-d bin ^
-sourcepath .\src ^
.\src\brandubh\util\*.java ^
.\src\brandubh\modelo\*.java ^
.\src\brandubh\control\*.java ^
.\src\brandubh\textui\*.java