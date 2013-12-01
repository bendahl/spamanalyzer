rm -rf target
cd spamanalyzer-datatypes
mvn clean install -DskipTests
cd ../stopforumspam
mvn clean install -DskipTests
cd ../spamanalyzer
mvn clean install -DskipTests
cd ..
mkdir -p target/plugins
cp stopforumspam/target/*with-dependencies.jar target/plugins
cp spamanalyzer/target/*.jar target
cp spamanalyzer-datatypes/target/*.jar target
