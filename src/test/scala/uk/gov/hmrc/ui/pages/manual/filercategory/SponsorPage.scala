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

package uk.gov.hmrc.ui.pages.manual.filercategory

import org.openqa.selenium.By
import uk.gov.hmrc.ui.pages.BasePage

object SponsorPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/filer-category/sponsor"

  private val foreignFiRadio: By    = By.cssSelector("input[name='value'][value='foreignFI']")
  private val nonFinancialRadio: By = By.cssSelector("input[name='value'][value='directNonFinancial']")
  private val trusteeRadio: By      = By.cssSelector("input[name='value'][value='trustee']")

  def checkPage(): this.type                                      = {
    onPage(pageUrl)
    this
  }
  def selectSponsorFilerType(sponsorFilerType: String): this.type = {
    onPage(pageUrl)
    click(sponsorFilerType match {
      case "foreignFI"          => foreignFiRadio
      case "directNonFinancial" => nonFinancialRadio
      case "trustee"            => trusteeRadio
      case other                => throw new IllegalArgumentException(s"Unknown SponsorFiler type: $other")
    })
    click(submitButtonId)
    this
  }
}
