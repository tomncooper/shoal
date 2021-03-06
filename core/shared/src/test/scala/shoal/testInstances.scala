/*
 * Copyright (c) 2020 the Shoal contributors.
 * See the project homepage at: https://splitbrain.io/shoal/
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

package shoal

import java.util.concurrent.atomic.AtomicLong

import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

import scala.collection.immutable.HashMap

object testInstances {

  private val minTs = 100L

  private def genTimestamps[Node : Arbitrary] = Gen.mapOf[Node,Long] {
    for {
      timestamp <- Gen.choose(0L, minTs)
      node <- arbitrary[Node]
    } yield node -> timestamp
  }

  implicit def arbitraryVClock[Node : Arbitrary]: Arbitrary[VectorClock[Node]] =
    Arbitrary(genTimestamps[Node].map(ts => VectorClock(timestamps = HashMap.from(ts), counter = new AtomicLong(minTs))))
}
