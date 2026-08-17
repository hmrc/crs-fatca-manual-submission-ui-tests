/*
 * Copyright 2025 HM Revenue & Customs
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

package uk.gov.hmrc.ui.utils

object TestData {

  val firstFi                 = "First FI"
  val fifthFi                 = "Fifth FI"
  val reportingYear           = "2025"
  val sponsorName             = "Fatca Sponsor"
  val sponsorGiin             = "98096B.00000.LE.350"
  val postCode                = "ZZ1 1ZZ"
  val iban                    = "IBAN"
  val oban                    = "OBAN"
  val isin                    = "ISIN"
  val osin                    = "OSIN"
  val semp                    = "SEMP"
  val anyOther                = "Any other"
  val postcodeSingleAddress   = "ZZ1Z 7AB"
  val postcodeMultipleAddress = "ZZ1 1ZZ"
  val postcodeSingleStaging   = "LE1 7AS"
  val postcodeMultipleStaging = "LL61 5AN"
  val accountIdentifier       = "ACC-12345"
  val currencyGbp             = "British Pound Sterling (GBP)"
  val country                 = "United States"

  private val env: String =
    System.getProperty("environment", "local").toLowerCase

  def postcodeSingleAddressEnv: String = env match {
    case "local"   => postcodeSingleAddress
    case "staging" => postcodeSingleStaging
    case _         => throw new IllegalArgumentException(s"Unsupported environment: $env")
  }

  def postcodeMultipleAddressEnv: String = env match {
    case "local"   => postcodeMultipleAddress
    case "staging" => postcodeMultipleStaging
    case _         => throw new IllegalArgumentException(s"Unsupported environment: $env")
  }
}
