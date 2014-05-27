ifeq ($(WINDIR),)
	S=:
else
	S=\;
endif

ifeq ($(SILENCE),true)
	override SILENCE=--silence
endif

all:
ifeq ($(SYSJHOME),)
	$(error SYSJHOME variable is not set)
endif
	$(JAVA_HOME)/bin/java -cp .$(S)$(SYSJHOME) JavaPrettyPrinter $(SILENCE) pacemaker.sysj

run:
	$(JAVA_HOME)/bin/java -cp .$(S)$(SYSJHOME)$(S)$(SYSJHOME)/* systemj.bootstrap.SystemJRunner pacemaker.xml

clean:
	rm -f *.class *.java
	rm -f org/pacemaker/*.class
