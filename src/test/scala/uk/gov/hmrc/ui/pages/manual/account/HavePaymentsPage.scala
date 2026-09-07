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

package uk.gov.hmrc.ui.pages.manual.account

import uk.gov.hmrc.ui.pages.BasePage
import uk.gov.hmrc.ui.utils.TestData

object HavePaymentsPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account/have-payments"

  def checkPageFATCA(): this.type = {
    onPage(pageUrl)
    checkH1(s"Were any payments made to this account, a payee or an owner in ${TestData.reportingYear}")
    this
  }
  def checkPageCRS(): this.type   = {
    onPage(pageUrl)
    checkH1(s"Were any payments made to this account in ${TestData.reportingYear}")
    this
  }
}
