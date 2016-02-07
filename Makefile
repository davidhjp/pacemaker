SYSJ_HOME=sysj
SYSJC=$(SYSJ_HOME)/bin/sysjc
SYSJR=$(SYSJ_HOME)/bin/sysjr

ifeq ($(WINDIR),)
	S=:
else
	S=\;
endif

ifeq ($(SILENCE),false)
	override SILENCE=
else
	override SILENCE=--silence
endif

all:
	$(SYSJC) $(SILENCE) --nojavac pacemaker.sysj

run-desktop: all
	javac -cp $(SYSJ_HOME)/lib/\*$(S). pace.java
	$(SYSJR) pacemaker.xml

run-jop: check all
	rm -rfv $(JOP_HOME)/java/target/src/sysjdemo
	mkdir $(JOP_HOME)/java/target/src/sysjdemo
	mkdir $(JOP_HOME)/java/target/src/sysjdemo/pacemaker
	$(SYSJR) -x pacemaker.xml
	sed -i '1ipackage pacemaker;' pacemaker.java
	sed -i '1ipackage pacemaker;' pace.java
	sed -r 's/(Vector currsigs)|(private (boolean|Signal|char|int))/static \1\2/g' -i pace.java
	cp -rv com org $(JOP_HOME)/java/target/src/sysjdemo
	cp -v pacemaker.java pace.java $(JOP_HOME)/java/target/src/sysjdemo/pacemaker
	make -C $(JOP_HOME) japp P1=sysjdemo P2=pacemaker P3=pacemaker

clean:
	rm -f *.class *.java
	rm -f org/pacemaker/*.class

check:
ifeq ($(JOP_HOME),)
	$(error JOP_HOME variable is not set)
endif
