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

package com.googlecode.furnace.grid

import org.gridgain.grid.spi.topology.basic.GridBasicTopologySpi
import org.gridgain.grid.{GridConfigurationAdapter, GridFactory}
import  GridFactory._
import util.Logger, Logger._

object GridBootstrapper {
  val stopAllJobs = true

  // See: http://www.gridgainsystems.com/wiki/display/GG15UG/Topology+SPI
  def startMasterNode {
    info("Starting master grid node")
    val toplogy = new GridBasicTopologySpi
    toplogy.setLocalNode(false)
    val config = new GridConfigurationAdapter
    config.setTopologySpi(toplogy)
    start(config);
  }

  def stopMasterNode {
    info("Stopping master grid node")
    stop(stopAllJobs);
  }

  def masterNode = getGrid

  //            // Execute Hello World task.
  //            GridTaskFuture<Integer> future = grid.execute(GridHelloWorldTask.class, "Hello World");
  //            // Wait for task completion.
  //            int phraseLen = future.get();
}
