/*
 *
 *  * Copyright 2017 Skymind,Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package org.deeplearning4j.scalnet.layers

import org.deeplearning4j.nn.conf.layers.{ DenseLayer, OutputLayer => JOutputLayer }
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction
import org.scalatest.{ Matchers, WordSpec }

class DenseTest extends WordSpec with Matchers {

  "A Dense layer" should {

    "have an input layer of shape (0, 100)" in {
      val DenseLayer = Dense(100)
      DenseLayer.inputShape shouldBe List(0)
    }

    "have an ouput layer of shape (0, 100)" in {
      val DenseLayer = Dense(100)
      DenseLayer.outputShape shouldBe List(100)
    }

    "compile to a DL4J Dense" in {
      val DenseLayer = Dense(100)
      val compiledLayer = DenseLayer.compile
      compiledLayer.isInstanceOf[DenseLayer] shouldBe true
    }

    "does not become an output layer when compiled without proper loss" in {
      val DenseLayer = Dense(100)
      val compiledLayer = DenseLayer.compile
      compiledLayer.isInstanceOf[JOutputLayer] shouldBe false
    }

    "does not become an output layer when converted to ouput layer without proper loss" in {
      val DenseLayer = Dense(100)
      val compiledLayer = DenseLayer.toOutputLayer(null)
      compiledLayer.isInstanceOf[JOutputLayer] shouldBe false
    }

    "become an output layer when compiled with proper loss" in {
      val DenseLayer = Dense(100, lossFunction = Option(LossFunction.NEGATIVELOGLIKELIHOOD))
      val compiledLayer = DenseLayer.compile
      compiledLayer.isInstanceOf[JOutputLayer] shouldBe true
    }

    "become an output layer when when converted to ouput layer with proper loss" in {
      val DenseLayer = Dense(100)
      val compiledLayer = DenseLayer.toOutputLayer(LossFunction.NEGATIVELOGLIKELIHOOD)
      compiledLayer.isInstanceOf[OutputLayer] shouldBe true
    }
  }
}
