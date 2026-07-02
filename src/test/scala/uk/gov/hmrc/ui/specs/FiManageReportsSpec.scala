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

class FiManageReportsSpec extends BaseSpec {

  Feature("Manage Reports Journey") {

    Scenario("Manage Reports Journey (Standard FI)", ManualSubmissionTests, SoloTests) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnManageReports()

      Then("The user is on the manage reports page")
      ManageReportsPage
        .onManageReportsPage()
        .checkPageHeading("Fifth FI")
        .checkCrsReportsSection()
        .checkFatcaReportsSection()

      When("The user clicks the upload XML file link")
      ManageReportsPage.clickUploadXmlLink()

      Then("The user is on the file upload page")
      FileUploadPage.onFileUploadPage()

      When("The user clicks the back link from the file upload page")
      FileUploadPage.clickOnBackLink()

      Then("The user is back on the manage reports page")
      ManageReportsPage.onManageReportsPage()

      When("The user clicks back to manage your CRS and FATCA reports")
      ManageReportsPage.clickBackToManageReports()

      Then("The user is on the FI management page")
      FiManagementPage.onPage()
    }

    Scenario("FATCA - Void journey", ManualSubmissionTests, SoloTests) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnManageReports()

      Then("The user is on the manage reports page")
      ManageReportsPage.onManageReportsPage()

      When("The user clicks void this information")
      ManageReportsPage.clickVoidThisInformation()

      Then("The user is on the voiding FATCA information page")
      VoidingFatcaInformationPage.checkDynamicPage()

      When("The user selects Yes and continues")
      VoidingFatcaInformationPage.selectYesAndContinue()

      Then("The user is on the FATCA information voided confirmation page")
      FatcaInformationVoidedPage.checkDynamicPage()
      FatcaInformationVoidedPage.checkPageHeading()

      //      When("The user clicks back to manage submitted reports")   --> Ticket raised for this : DAC6-4324
      //      FatcaInformationVoidedPage.clickBackToManageSubmittedReports()
      And("The user clicks back to manage CRS and FATCA reports")
      FatcaInformationVoidedPage.clickBackToManageCrsAndFatca()
      Then("The user is on the FI management page")
      FiManagementPage.onPage()
    }

    Scenario("CRS Elections journey - pre 2026 (no CARF)", ManualSubmissionTests, SoloTests) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnSecondManageReports()

      ManageReportsPage.selectReportingYear("2022")

      When("The user clicks the view or make elections link")
      ManageReportsPage.clickViewOrMakeElectionsForFI()

      Then("The user is on the manage elections page")
      ManageElectionsPage
        .onManageElectionsPage()
        .checkPageHeading("First FI", "2022")
        .checkCrsElectionsSection()
        .checkFatcaElectionsSection()

      When("The user clicks Make CRS elections")
      ManageElectionsPage.clickMakeCrsElections()

      When("The user selects Yes on the CRS contracts page")
      CrsContractsPage.selectYesAndContinue()

      When("The user selects Yes on the CRS dormant accounts page")
      CrsDormantAccountsPage.selectYesAndContinue()

      When("The user selects Yes on the CRS thresholds page")
      CrsThresholdsPage.selectYesAndContinue()

      And("The user selects Confirm and Send on check answers page")
      ElectionsCheckAnswersPage.confirmAndSend()

      Then("The user completes the elections-sent journey")
      ElectionsSentPage.checkDynamicPage()

    }

    Scenario("CRS Elections journey - 2026 onwards (with CARF)", ManualSubmissionTests, SoloTests) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnSecondManageReports()

      And("The user navigates to the 2026 reporting year")
      ManageReportsPage.selectReportingYear("2026")

      When("The user clicks the view or make elections link")
      ManageReportsPage.clickViewOrMakeElectionsForFI()

      Then("The user is on the manage elections page for 2026")
      ManageElectionsPage
        .onManageElectionsPage()
        .checkPageHeading("First FI", "2026")
        .checkCrsElectionsSection()
        .checkFatcaElectionsSection()

      When("The user clicks Make CRS elections")
      ManageElectionsPage.clickMakeCrsElections()

      When("The user selects Yes on the CRS contracts page")
      CrsContractsPage.selectYesAndContinue()

      When("The user selects Yes on the CRS dormant accounts page")
      CrsDormantAccountsPage.selectYesAndContinue()

      When("The user selects Yes on the CRS thresholds page")
      CrsThresholdsPage.selectYesAndContinue()

      When("The user selects Yes on the CARF reporting page")
      CrsCarfGrossProceedsPage.selectYesAndContinue()

      When("The user selects Yes on the CARF under CRS page")
      CrsCarfGrossProceedsUnderCrsPage.selectYesAndContinue()

      And("The user selects Confirm and Send on check answers page")
      ElectionsCheckAnswersPage.confirmAndSend()

      Then("The user completes the elections-sent journey")
      ElectionsSentPage.checkDynamicPage()
    }

    Scenario("CRS Elections 2026 - Change all answers from Yes to No", ManualSubmissionTests, SoloTests) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnSecondManageReports()

      And("The user navigates to the 2026 reporting year")
      ManageReportsPage.selectReportingYear("2026")

      And("The user clicks the view or make elections link")
      ManageReportsPage.clickViewOrMakeElectionsForFI()

      And("The user completes the CRS elections journey answering Yes to all questions")
      ManageElectionsPage.onManageElectionsPage()
      ManageElectionsPage.clickMakeCrsElections()
      CrsContractsPage.selectYesAndContinue()
      CrsDormantAccountsPage.selectYesAndContinue()
      CrsThresholdsPage.selectYesAndContinue()
      CrsCarfGrossProceedsPage.selectYesAndContinue()
      CrsCarfGrossProceedsUnderCrsPage.selectYesAndContinue()

      When("The user clicks Change for contracts and selects No")
      ElectionsCheckAnswersPage.clickChangeContracts()
      CrsContractsPage.selectNoAndContinueFromChange()

      When("The user clicks Change for dormant accounts and selects No")
      ElectionsCheckAnswersPage.clickChangeDormant()
      CrsDormantAccountsPage.selectNoAndContinueFromChange()

      When("The user clicks Change for thresholds and selects No")
      ElectionsCheckAnswersPage.clickChangeThresholds()
      CrsThresholdsPage.selectNoAndContinueFromChange()

      When("The user clicks Change for CARF and selects No")
      ElectionsCheckAnswersPage.clickChangeCarf()
      CrsCarfGrossProceedsPage.selectNoAndContinueFromChange()

      And(
        "The user is back on the CRS check your answers page with all answers updated to No and click the confirm and send"
      )
      ElectionsCheckAnswersPage.confirmAndSend()

      Then("The user completes the elections-sent journey")
      ElectionsSentPage.checkDynamicPage()

    }

    Scenario("FATCA Elections journey - 2026", ManualSubmissionTests, SoloTests) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnSecondManageReports()

      And("The user navigates to the 2026 reporting year")
      ManageReportsPage.selectReportingYear("2026")

      When("The user clicks the view or make elections link")
      ManageReportsPage.clickViewOrMakeElectionsForFI()

      Then("The user is on the manage elections page for 2026")
      ManageElectionsPage
        .onManageElectionsPage()
        .checkPageHeading("First FI", "2026")
        .checkCrsElectionsSection()
        .checkFatcaElectionsSection()

      When("The user clicks Make FATCA elections")
      ManageElectionsPage.clickMakeFatcaElections()

      When("The user selects Yes on the US Treasury Regulations page")
      FatcaUSTreasuryRegulationsPage.selectYesAndContinue()

      When("The user selects Yes on the FATCA thresholds page")
      FatcaThresholdsPage.selectYesAndContinue()

      And("The user selects Confirm and Send on check answers page")
      ElectionsCheckAnswersPage.confirmAndSend()

      Then("The user completes the elections-sent journey")
      ElectionsSentPage.checkDynamicPage()
    }

    Scenario("FATCA Elections 2026 - Change all answers from Yes to No", ManualSubmissionTests, SoloTests) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnSecondManageReports()

      And("The user navigates to the 2026 reporting year")
      ManageReportsPage.selectReportingYear("2026")

      When("The user clicks the view or make elections link")
      ManageReportsPage.clickViewOrMakeElectionsForFI()

      And("The user completes the FATCA elections journey answering Yes to all questions")
      ManageElectionsPage.onManageElectionsPage()
      ManageElectionsPage.clickMakeFatcaElections()
      FatcaUSTreasuryRegulationsPage.selectYesAndContinue()
      FatcaThresholdsPage.selectYesAndContinue()

      When("The user clicks Change for US Treasury Regulations and selects No")
      ElectionsCheckAnswersPage.clickChangeFatcaUsTreasury()
      FatcaUSTreasuryRegulationsPage.selectNoAndContinueFromChange()

      When("The user clicks Change for FATCA thresholds and selects No")
      ElectionsCheckAnswersPage.clickChangeFatcaThresholds()
      FatcaThresholdsPage.selectNoAndContinueFromChange()

      And(
        "The user is back on the FATCA check your answers page with all answers updated to No and selects confirm and send"
      )
      ElectionsCheckAnswersPage.confirmAndSend()

      Then("The user completes the elections-sent journey")
      ElectionsSentPage.checkDynamicPage()

    }

    Scenario(
      "Manage Elections - user can navigate through all years via pagination",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnManageReports()

      And("The user clicks the view or make elections link")
      ManageReportsPage.clickViewOrMakeElectionsForFI()

      Then("The manage elections page defaults to the reporting year")
      ManageElectionsPage
        .onManageElectionsPage()
        .checkDefaultYear()

      When("The user navigates back through each year to the earliest year")
      ManageElectionsPage.navigateThroughAllPreviousYears()

      Then("The manage elections page is shown for the earliest year")
      ManageElectionsPage.checkEarliestYear()

      When("The user navigates forward to the current year")
      ManageElectionsPage.navigateToCurrentYear()

      Then("The manage elections page is shown for the current year")
      ManageElectionsPage.checkCurrentYear()
    }
  }
}
