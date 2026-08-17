/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.ui.pages.manual.sponsor

import uk.gov.hmrc.ui.pages.BasePage
import uk.gov.hmrc.ui.utils.TestData

object RemoveTaxResidentCountriesPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/sponsor/remove-tax-resident-country"

  def checkPage(): this.type = {
    checkH1(
      s"Are you sure you want to remove ${TestData.residentForTax} as a tax resident country for ${TestData.sponsorName}?"
    )
    this
  }

}
