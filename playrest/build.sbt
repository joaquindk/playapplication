name := """playrest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
"com.typesafe.play.plugins" %% "play-plugins-redis" % "2.3.1",
"com.typesafe.play" %% "play-cache" % "2.2.0",
"org.mongodb" % "mongo-java-driver" % "2.8.0"
)

resolvers += "Sedis repository" at "http://pk11-scratch.googlecode.com/svn/trunk/"
