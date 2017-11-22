JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	TreeSearch.java \
	Node.java \
	LeafNode.java \
	TreeNode.java \
	DataNode.java \
	CustomMap.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class