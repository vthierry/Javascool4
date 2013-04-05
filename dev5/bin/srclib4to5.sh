
#
# Effectue la copie des source jvs4 pour leur utilisation dans jvs5
#   ce script est un mecanisme temporaire et local à l'environnement de vthierry, il n'a pas vocation à être utilisé après la ferneture de jvs4
#

# Positionnement à la racine du svn
cd /home/vthierry/Work/culsci/jvs4

# Copy des libs nécessaires
for f in javac7 RSyntaxTextArea json java2html javadoc7
do make -f - <<EOF
dev5/lib4/$f.jar : work/lib/$f.jar
	cp \$^ \$@
EOF
done
echo "CE REPERTOIRE MIROIRE LES LIB DE JVS4 : NE PAS TOUCHER" > dev5/lib4/README.TXT

# Copy des srcs nécessaires
for l in `ls work/src/org/javascool/{{Core,About,package},{core,gui,macros,tools,widgets,widgets/icons}/*}.{java,xml,png,htm,html} 2> /dev/null`
do f=`echo $l | sed 's/work\/src//'` ; make  -f - <<EOF
dev5/src4/$f : work/src/$f
	mkdir -p \$(@D)
	cp \$^ \$(@D)
EOF
done
for l in `ls dev5/src5/org/javascool/core/*.{java,zip}`
do f=`echo $l | sed 's/dev5\/src5//'` ; make  -f - <<EOF
dev5/src4/$f : dev5/src5/$f
	mkdir -p \$(@D)
	cp \$^ \$(@D)
EOF
done
echo "CE REPERTOIRE MIROIRE LES SRC DE JVS4 ET JVS5 : NE PAS TOUCHER" > dev5/src4/README.TXT


