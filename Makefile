ifeq ($(WINDIR),)
	S=:
else
	S=\;
endif

all:
ifeq ($(SYSJHOME),)
	$(error SYSJHOME variable is not set)
endif
	java -cp .$(S)$(SYSJHOME) JavaPrettyPrinter pacemaker.sysj

clean:
	rm -f *.class *.java
