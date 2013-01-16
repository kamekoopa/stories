import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "stories"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "commons-codec"                % "commons-codec"            % "1.7",
      "org.apache.commons"           % "commons-lang3"            % "3.1",
      "com.google.inject"            % "guice"                    % "3.0",
      "com.google.inject.extensions" % "guice-assistedinject"     % "3.0",
      "org.thymeleaf"                % "thymeleaf"                % "2.0.15",
      "nz.net.ultraq.web.thymeleaf"  % "thymeleaf-layout-dialect" % "1.0.5"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
