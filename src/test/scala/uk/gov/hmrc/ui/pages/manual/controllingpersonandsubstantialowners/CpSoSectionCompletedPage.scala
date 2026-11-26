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

package uk.gov.hmrc.ui.pages.manual.controllingpersonandsubstantialowners

import uk.gov.hmrc.ui.pages.BasePage

object CpSoSectionCompletedPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/is-cp-so-section-completed"

  private def role(regime: String): String =
    if (regime == "CRS") "controlling persons" else "substantial owners"

  def checkPage(regime: String, fiName: String): this.type = {
    onPage(pageUrl)
    checkH1(s"Have you finished adding the ${role(regime)} for $fiName?")
    this
  }
}
