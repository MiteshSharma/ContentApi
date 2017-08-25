name := """content-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(SbtWeb).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

resolvers ++= Seq(
  "pk11 repo" at "http://pk11-scratch.googlecode.com/svn/trunk",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
  "google-sedis-fix" at "http://pk11-scratch.googlecode.com/svn/trunk/",
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/maven-releases/",
  "Typesafe snapshot repository" at "http://repo.typesafe.com/typesafe/snapshots/",
  Resolver.sonatypeRepo("releases")
)

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  filters,
  "com.typesafe.play" % "play-cache_2.11" % "2.5.9",
  "org.pac4j" % "pac4j-cas" % "1.9.4",
  "org.pac4j" % "pac4j-core" % "1.9.4",
  "org.pac4j" % "pac4j-http" % "1.9.4",
  "org.pac4j" % "play-pac4j" % "2.5.2",
  "org.mongodb" % "mongo-java-driver" % "3.3.0",
  "com.typesafe.play.modules" %% "play-modules-redis" % "2.4.1",
  "org.mongodb.morphia" % "morphia" % "1.2.1",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "com.nimbusds" % "nimbus-jose-jwt" % "4.27",
  "eu.bitwalker" % "UserAgentUtils" % "1.20",
  "com.amazonaws" % "aws-java-sdk" % "1.11.73"
)

pipelineStages in Assets := Seq(concat, uglify, digest, gzip)

UglifyKeys.compress in Assets := true
UglifyKeys.mangle in Assets := true
excludeFilter in uglify := (excludeFilter in uglify).value || GlobFilter("*.min.js")
includeFilter in uglify := GlobFilter("javascripts/*.js")

Concat.groups := Seq(
  "css/main-css.css" -> group(Seq(
    "css/bootstrap.min.css",
    "css/googlefont.css",
    "css/app.css"
  )))