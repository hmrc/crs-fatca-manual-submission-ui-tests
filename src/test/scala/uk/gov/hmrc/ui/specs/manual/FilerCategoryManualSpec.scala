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

package uk.gov.hmrc.ui.specs.manual

import uk.gov.hmrc.ui.pages.manual.*
import uk.gov.hmrc.ui.pages.*
import uk.gov.hmrc.ui.specs.BaseSpec
import uk.gov.hmrc.ui.specs.tags.{ManualSubmissionTests, SoloTests}

class FilerCategoryManualSpec extends BaseSpec {

  Feature("CRS/FATCA Manual - Sponsor journey (FATCA)") {

    Scenario("Filer-Category journey with Reporting Fi", ManualSubmissionTests, SoloTests) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnSecondManageReports()

      And("The user clicks 'filling in an online form for manual reporting'")
      ManageReportsPage.clickFillInOnlineManualReport()

      And("They select FATCA and continue")
      CrsOrFatcaPage.selectReportType("FATCA")

      And("They enter a valid year and continue")
      ReportingDetailsYearPage.enterYearAndContinue()

      And("They select a report with information and continue")
      TypeOfReportPage.selectReportWithInformationAndContinue()

      And("They save and continue on the check answers page")
      ReportCheckAnswersPage.confirmAndSend()

      Then("They are on the manual send a report task list page")
      ManualSendAReportIndexPage.checkDynamicPage()

      When("They open the Filer-category task")
      ManualSendAReportIndexPage.clickFilerCategory()

    }
  }
}
