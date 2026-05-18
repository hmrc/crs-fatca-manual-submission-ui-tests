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
        .checkPageHeading()
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

      When("The user clicks back to manage submitted reports")
      FatcaInformationVoidedPage.clickBackToManageSubmittedReports()
      And("The user clicks back to manage CRS and FATCA reports")
      FatcaInformationVoidedPage.clickBackToManageCrsAndFatca()
      Then("The user is on the FI management page")
      FiManagementPage.onPage()
    }
  }
}
