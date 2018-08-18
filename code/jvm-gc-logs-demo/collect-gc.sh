$JAVA_8_HOME/bin/java \
-XX:+UnlockDiagnosticVMOptions      \
-XX:+PrintCompilation -XX:+PrintInlining      \
-XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps      \
-XX:+PrintGCApplicationStoppedTime      \
-XX:+PrintGCApplicationConcurrentTime      \
-XX:+PrintTenuringDistribution      \
-XX:+PrintAdaptiveSizePolicy      \
-Xloggc:/kaaj/source/blog/code/jvm-gc-logs-demo/logs/algo-gc.log	\
-cp target/jvm-gc-logs-demo.jar com.swayam.demo.jvm.gclog.HeavyLiftingCalculator
