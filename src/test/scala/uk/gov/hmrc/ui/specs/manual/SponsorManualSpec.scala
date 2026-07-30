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
import uk.gov.hmrc.ui.pages.manual.sponsor.*
import uk.gov.hmrc.ui.specs.BaseSpec
import uk.gov.hmrc.ui.specs.tags.*
import uk.gov.hmrc.ui.utils.TestData

class SponsorManualSpec extends BaseSpec with SponsorJourneyHelper {

  Feature("CRS/FATCA Manual - Sponsor journey (FATCA)") {

    Scenario(
      "Sponsor - complete journey up to UK postcode (FATCA)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnSecondManageReports()

      And("The user clicks 'filling in an online form for manual reporting'")
      ManageReportsPage.clickFillInOnlineManualReport()

      And("They select FATCA and continue")
      CrsOrFatcaPage.selectFatcaAndContinue()

      And("They enter a valid year and continue")
      ReportingDetailsYearPage.enterYearAndContinue()

      And("They select a report with information and continue")
      TypeOfReportPage.selectReportWithInformationAndContinue()

      And("They save and continue on the check answers page")
      ReportCheckAnswersPage.confirmAndSend()

      Then("They are on the manual send a report task list page")
      ManualSendAReportIndexPage.checkDynamicPage()

      When("They open the sponsor details task")
      ManualSendAReportIndexPage.clickSponsorDetails()

      And("They confirm they have a sponsor and continue")
      HaveASponsorPage.selectYesAndContinue()

      And("They enter the sponsor name and continue")
      SponsorNamePage.enterSponsorNameAndContinue()

      Then("They are on the sponsor GIIN page and it shows the sponsor name")
      SponsorGiinPage.checkLabelForSponsor()

      And("They enter the GIIN and continue")
      SponsorGiinPage.enterGiinAndContinue()

      And("They confirm the sponsor is UK-based and continue")
      SponsorWhereBasedPage.selectYesAndContinue()

      Then("They are on the sponsor UK postcode page")
      SponsorUkPostcodePage.checkPage()
      When("They enter a postcode that returns multiple addresses and find the address")
      SponsorUkPostcodePage.enterPostcodeAndFindAddress(TestData.postcodeMultipleAddressEnv)

      Then("They are on the sponsor select address page")
      SponsorSelectAddressPage.checkPage()

      When("They select the first address and continue")
      SponsorSelectAddressPage.selectFirstAddressAndContinue()

    }

    Scenario(
      "Sponsor address - single address returned goes to is-this-the-address (FATCA)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has completed the sponsor journey up to the Where are they based page")
      navigateToWhereAreTheyBased()

      When("The sponsor is UK-based and continue")
      SponsorWhereBasedPage.selectYesAndContinue()

      And("The sponsor enter a postcode that returns one address and find the address")
      SponsorUkPostcodePage.enterPostcodeAndFindAddress(TestData.postcodeSingleAddressEnv)

      Then("They are on the sponsor is-this-the-address page")
      SponsorIsThisTheAddressPage.checkPage()
      SponsorIsThisTheAddressPage.checkCountryNotDisplayed("United Kingdom")

      When("They confirm the address and continue")
      SponsorIsThisTheAddressPage.selectYesAndContinue()
    }

    Scenario("Sponsor journey - Address Non-UK", ManualSubmissionTests, SoloTests) {
      Given("The user have completed the sponsor journey up to Where are they based page")
      navigateToWhereAreTheyBased()

      When("The Sponsor is Non-UK based and continue")
      SponsorWhereBasedPage.selectNoAndContinue()

      And("The user entered the Non-UK address manually")
      SponsorNonUKAddressPage.enterAddressNonUK()
    }
  }
}
