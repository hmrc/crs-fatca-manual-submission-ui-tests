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

import uk.gov.hmrc.ui.pages.*
import uk.gov.hmrc.ui.pages.manual.*
import uk.gov.hmrc.ui.specs.BaseSpec
import uk.gov.hmrc.ui.specs.tags.*

class ReportingFinancialInstitutionManualSpec extends BaseSpec with ManualJourneyHelper {

  Feature("CRS/FATCA Manual - Check the reporting financial institution details") {

    Scenario(
      "CRS - FI is the User shows no TIN rows and the registered UTR paragraph",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS for the registered business (CT enrolled)")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")

      When("They open the reporting financial institution task")
      ReportingFinancialInstitutionPage.openFromTaskList()

      Then("They see name and address only, with the change link going to the registered business")
      ReportingFinancialInstitutionPage.checkPage("First FI")
      ReportingFinancialInstitutionPage.checkChangeLink("First FI")
      ReportingFinancialInstitutionPage.checkRegisteredBusinessChangeLink("First FI")
      ReportingFinancialInstitutionPage.checkNameAndAddress("First FI")
      ReportingFinancialInstitutionPage.checkNoTinRows()

      And("They see the registered UTR paragraph and the question")
      ReportingFinancialInstitutionPage.checkRegisteredUtrParagraph("1234567890")
      ReportingFinancialInstitutionPage.checkQuestion("First FI")

      When("They select Yes and continue")
      ReportingFinancialInstitutionPage.selectYesAndContinue()

      Then("They are returned to the send a report page")
      ReportingFinancialInstitutionPage.checkReturnedToSendAReport()
    }

    Scenario("CRS - Trust URN is shown and the answer Yes is saved", ManualSubmissionTests, SoloTests) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("ThirdFI")
      navigateToTaskList("CRS")

      When("They open the reporting financial institution task")
      ReportingFinancialInstitutionPage.openFromTaskList()

      Then("They see the FI details with the Trust URN and the question")
      ReportingFinancialInstitutionPage.checkPage("Third FI")
      ReportingFinancialInstitutionPage.checkChangeLink("Third FI")
      ReportingFinancialInstitutionPage.checkNameAndAddress("Third FI")
      ReportingFinancialInstitutionPage.checkRow("Trust Unique Reference Number", "ABTRUST12345678")
      ReportingFinancialInstitutionPage.checkRowAbsent("Unique Taxpayer Reference")
      ReportingFinancialInstitutionPage.checkRowAbsent("Company Registration Number")
      ReportingFinancialInstitutionPage.checkQuestion("Third FI")

      When("They select Yes and continue")
      ReportingFinancialInstitutionPage.selectYesAndContinue()

      Then("They are returned to the send a report page")
      ReportingFinancialInstitutionPage.checkReturnedToSendAReport()
    }

    Scenario(
      "CRS - Company Registration Number is shown and the answer No is saved",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("CRS")

      When("They open the reporting financial institution task")
      ReportingFinancialInstitutionPage.openFromTaskList()

      Then("They see the FI details with the CRN")
      ReportingFinancialInstitutionPage.checkPage("Fourth FI")
      ReportingFinancialInstitutionPage.checkNameAndAddress("Fourth FI")
      ReportingFinancialInstitutionPage.checkRow("Company Registration Number", "AC012345")
      ReportingFinancialInstitutionPage.checkRowAbsent("Unique Taxpayer Reference")
      ReportingFinancialInstitutionPage.checkQuestion("Fourth FI")

      When("They select No and continue")
      ReportingFinancialInstitutionPage.selectNoAndContinue()

      Then("They are returned to the send a report page")
      ReportingFinancialInstitutionPage.checkReturnedToSendAReport()
    }

    Scenario("CRS - UTR and Company Registration Number are both shown", ManualSubmissionTests, SoloTests) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FifthFI")
      navigateToTaskList("CRS")

      When("They open the reporting financial institution task")
      ReportingFinancialInstitutionPage.openFromTaskList()

      Then("They see both the UTR and the CRN rows")
      ReportingFinancialInstitutionPage.checkPage("Fifth FI")
      ReportingFinancialInstitutionPage.checkChangeLink("Fifth FI")
      ReportingFinancialInstitutionPage.checkRow("Unique Taxpayer Reference", "1234567890")
      ReportingFinancialInstitutionPage.checkRow("Company Registration Number", "AC012345")
      ReportingFinancialInstitutionPage.checkQuestion("Fifth FI")
    }

    Scenario("CRS - no identification numbers shows NOTIN", ManualSubmissionTests, SoloTests) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("SecondFI")
      navigateToTaskList("CRS")

      When("They open the reporting financial institution task")
      ReportingFinancialInstitutionPage.openFromTaskList()

      Then("They see the Identification number row with NOTIN wording")
      ReportingFinancialInstitutionPage.checkPage("Second FI")
      ReportingFinancialInstitutionPage.checkRow("Identification number", ReportingFinancialInstitutionPage.notinValue)
      ReportingFinancialInstitutionPage.checkQuestion("Second FI")
    }

    Scenario("FATCA - GIIN is shown with the question", ManualSubmissionTests, SoloTests) {
      Given("The user has reached the manual task list as FATCA")
      journeyToYourFis()
      selectTheSuitableFI("SecondFI")
      navigateToTaskList("FATCA")

      When("They open the reporting financial institution task")
      ReportingFinancialInstitutionPage.openFromTaskList()

      Then("They see the GIIN row and the question")
      ReportingFinancialInstitutionPage.checkPage("Second FI")
      ReportingFinancialInstitutionPage.checkChangeLink("Second FI")
      ReportingFinancialInstitutionPage.checkNameAndAddress("Second FI")
      ReportingFinancialInstitutionPage.checkRow("Global Intermediary Identification Number", "98096B.00000.LE.555")
      ReportingFinancialInstitutionPage.checkQuestion("Second FI")
    }

    Scenario("FATCA - no GIIN shows the must-provide text and hides the question", ManualSubmissionTests, SoloTests) {
      Given("The user has reached the manual task list as FATCA")
      journeyToYourFis()
      selectTheSuitableFI("FifthFI")
      navigateToTaskList("FATCA")

      When("They open the reporting financial institution task")
      ReportingFinancialInstitutionPage.openFromTaskList()

      Then("They see GIIN Not provided, the must-provide link and no question")
      ReportingFinancialInstitutionPage.checkPage("Fifth FI")
      ReportingFinancialInstitutionPage.checkMustProvideGiinLink("Fifth FI")
      ReportingFinancialInstitutionPage.checkRow("GIIN", "Not provided")
      ReportingFinancialInstitutionPage.checkQuestionHidden()
    }
  }
}
