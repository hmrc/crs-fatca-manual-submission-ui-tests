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

package uk.gov.hmrc.ui.specs

import uk.gov.hmrc.ui.pages.*
import uk.gov.hmrc.ui.specs.tags.*

class CrsFatcaManualSpec extends BaseSpec {

  Feature("CRS/FATCA Manual - Report details (CRS or FATCA & reporting year)") {

    Scenario("CRS/FATCA Manual - navigate to the CRS or FATCA report page", ManualSubmissionTests, SoloTests) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnSecondManageReports()

      And("The user clicks 'filling in an online form for manual reporting'")
      ManageReportsPage.clickFillInOnlineManualReport()

      When("They are on the 'Is this a CRS or FATCA report?' page")
      CrsOrFatcaPage.selectCrsAndContinue()

      And("They enter a valid year and continue")
      ReportingDetailsYearPage.enterYearAndContinue("2025")

    }

  }
}
