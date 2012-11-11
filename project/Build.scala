import sbt._
import Keys._
import com.gu._
import com.gu.SbtDistPlugin._

object TeamCitySbtRunnerBuild extends Build {


  val appName         = "sbt-runner"

    lazy val root = Project(id = "sbt-runner", base = file("."),
      settings = Defaults.defaultSettings ++ SbtDistPlugin.distSettings
    ).aggregate(common, server, agent).settings(
      distFiles <+= (packageBin in (agent, Compile)) map { _ -> ("agent/"+appName+".zip") },
      distFiles <+= (packageBin in (server, Compile)) map { _ -> ("server/"+appName+".zip") },
      distFiles <+= baseDirectory map { dir =>
        dir / "teamcity-plugin.xml" -> "teamcity-plugin.xml"
      }

      //  (baseDirectory / "teamcity-plugin.xml" -> "teamcity-plugin.xml")
    )


  // def jarProject(project: Project, outputPath: String) = packageJar in (project, Compile) map { _ -> outputPath }

  lazy val common = Project(id = "common", base = file("common"))

    lazy val agent = Project(id = "agent", base = file("agent")) dependsOn(common)
    
    lazy val server = Project(id = "server", base = file("server")) dependsOn(common)

}
