/*
 * Copyright 2006-2008 Workingmouse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.furnace.util

import org.apache.log4j.{Appender, PatternLayout, Level, BasicConfigurator}
import Console._
import com.googlecode.furnace.util.javas.Enumeration._
import org.apache.log4j.BasicConfigurator._
import org.apache.log4j.Level._
import org.apache.log4j.Logger._

object Logger {
  lazy val furnaceCategory = "com.googlecode.furnace"
  lazy val rootLogger = getRootLogger
  lazy val logger = org.apache.log4j.Logger.getLogger(furnaceCategory)
  lazy val layout = new PatternLayout("[%d{yyyy-MM-dd HH:mm:ss}] %5p [%c{1}] - %m%n")

  def configureLogging {
    configure
    rootLogger.setLevel(WARN)
    enumToList(rootLogger.getAllAppenders).asInstanceOf[List[Appender]].foreach(_.setLayout(layout))
    getLogger(furnaceCategory).setLevel(INFO)
    configureGridGainLogging
  }

  def info(message: => String) {
    logger.info(message)
  }

  def error(message: => String) {
    logger.error(message)
  }

  private def configureGridGainLogging {
    getLogger("org.jboss.serial").setLevel(WARN)
    getLogger("org.gridgain").setLevel(WARN)
    getLogger("org.gridgain.grid.kernal").setLevel(INFO)
    getLogger("org.gridgain.grid.kernal.GridKernal").setLevel(INFO)
    getLogger("org.gridgain.grid.kernal.managers.communication").setLevel(WARN)
    getLogger("org.gridgain.grid.kernal.managers.deployment").setLevel(WARN)
    getLogger("org.gridgain.grid.kernal.managers.discovery").setLevel(WARN)
    getLogger("org.gridgain.grid.spi.discovery.multicast").setLevel(WARN)
    getLogger("org.gridgain.grid.spi.discovery.jgroups").setLevel(WARN)
    getLogger("org.gridgain.grid.spi.collision").setLevel(WARN)
    getLogger("org.gridgain.grid.spi.failover").setLevel(WARN)
    getLogger("org.gridgain.grid.spi.metrics").setLevel(WARN)
    getLogger("org.gridgain.grid.spi.topology").setLevel(WARN)
    getLogger("org.springframework").setLevel(WARN)
  }
}