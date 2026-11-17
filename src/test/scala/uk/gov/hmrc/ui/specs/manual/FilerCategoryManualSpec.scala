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
import uk.gov.hmrc.ui.pages.manual.filercategory.*
import uk.gov.hmrc.ui.pages.*
import uk.gov.hmrc.ui.specs.BaseSpec
import uk.gov.hmrc.ui.specs.tags.{ManualSubmissionTests, SoloTests}

class FilerCategoryManualSpec extends BaseSpec with ManualJourneyHelper {

  Feature("CRS/FATCA Manual - Sponsor journey (FATCA)") {

    Scenario(
      "Filer-Category journey with Reporting Fi - the link is enabled once the sponsor first page is answered, then the section is Completed",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnManageReports("FourthFI")

      And("The user clicks 'filling in an online form for manual reporting'")
      ManageReportsPage.clickFillInOnlineManualReport()

      And("They select FATCA and continue")
      CrsOrFatcaPage.selectReportType("FATCA")

      And("They enter a valid year and continue")
      ReportingDetailsYearPage.enterYearAndContinue()

      And("They select a report with information and continue")
      TypeOfReportPage.selectReportWithInformationAndContinue()

      And("They save and continue on the check answers page")
      ReportCheckAnswersPage.clickCreateDraft()

      Then("Filer category cannot be started yet, so it shows the message and no link")
      SendAReportSectionStatusPage.checkFilerCategoryStatus("Cannot start yet")
      SendAReportSectionStatusPage.checkFilerCategoryIsNotALink()

      When("They answer the first sponsor page by selecting No to having a sponsor")
      SendAReportSectionStatusPage.clickSponsorDetails()
      SponsorCompletionPage.checkHaveSponsorPage("Fourth FI")
      SponsorCompletionPage.selectNoAndContinue()
      SponsorCompletionPage.checkCheckAnswersPage("Fourth FI")

      And("They go back to the task list")
      SendAReportSectionStatusPage.goToPage()

      Then("Filer category is now Incomplete and the link is enabled")
      SendAReportSectionStatusPage.checkFilerCategoryStatus("Incomplete")

      When("They open the Filer-category task")
      ManualSendAReportIndexPage.clickFilerCategory()

      Then("They are on the reporting FI filer type page")
      ReportingFiPage.checkPage()

      When("They select the foreign FI filer type and continue")
      ReportingFiPage.selectFiFilerTyep("foreign")

      When("They select Continue on the check answers page")
      CheckAnswersPage.continueFromCheckAnswers()

      Then("Filer category is Completed")
      SendAReportSectionStatusPage.checkFilerCategoryStatus("Completed")
    }

    Scenario(
      "Filer-Category - going to check answers without answering shows the problem page and the section stays Incomplete",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA and answered No to having a sponsor")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("FATCA")
      SendAReportSectionStatusPage.clickSponsorDetails()
      SponsorCompletionPage.selectNoAndContinue()
      SponsorCompletionPage.checkCheckAnswersPage("First FI")

      When("They go straight to the filer category check answers page")
      CheckAnswersPage.goToCheckAnswers()

      When("They go back to the task list")
      SendAReportSectionStatusPage.goToPage()

      Then("Filer category is still Incomplete")
      SendAReportSectionStatusPage.checkFilerCategoryStatus("Incomplete")
    }
  }
}
