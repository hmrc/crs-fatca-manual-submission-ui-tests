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
      "Sponsor address - multiple addresses returned goes to select-address (FATCA)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has completed the sponsor journey up to the Where are they based page")
      navigateToWhereAreTheyBased()

      When("The sponsor is UK-based and continue")
      SponsorWhereBasedPage.selectYesAndContinue()

      Then("They are on the sponsor UK postcode page")
      SponsorUkPostcodePage.checkPage()
      When("They enter a postcode that returns multiple addresses and find the address")
      SponsorUkPostcodePage.enterPostcodeAndFindAddress(TestData.postcodeMultipleAddressEnv)

      Then("They are on the sponsor select address page")
      SponsorSelectAddressPage.checkPage()

      When("They select the first address and continue")
      SponsorSelectAddressPage.selectFirstAddressAndContinue()

      And("Enter the sponsor resident for tax")
      ResidentForTaxPage.addResidentCountryForTax()

      Then("The tax resident countries display one country added")
      TaxResidentCountriesPage.onPage()

      And("Select No to add another tax resident countries")
      TaxResidentCountriesPage.selectNoAndContinue()

      Then("They are on the sponsor check answers page")
      SponsorCheckAnswersPage.checkPage()

      And("The summary shows the sponsor details")
      SponsorCheckAnswersPage
        .checkSummaryContains("Yes")
        .checkSummaryContains(TestData.sponsorName)
        .checkSummaryContains(TestData.sponsorGiin)
        .checkSummaryContains("United Kingdom")

      And("The tax resident countries card shows the resident country")
      SponsorCheckAnswersPage.checkTaxResidentContains(TestData.residentForTax)

      When("They continue from the check answers page")
      SponsorCheckAnswersPage.continueFromCheckAnswers()

      Then("They are returned to the manual send a report task list page")
      ManualSendAReportIndexPage.onPage()

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

      And("Enter the sponsor resident for tax")
      ResidentForTaxPage.addResidentCountryForTax()

      Then("The tax resident countries display one country added")
      TaxResidentCountriesPage.onPage()

      And("Select Yes to add another tax resident countries")
      TaxResidentCountriesPage.selectYesAndContinue()

      And("Enter another sponsor resident for tax")
      ResidentForTaxPage.addResidentCountryForTax("Iceland")

      When("Click to remove a country from tax resident countries list") // need to assert 2 countries?
      TaxResidentCountriesPage.removeCountry("Iceland")

      Then("Confirm to remove country")
      RemoveTaxResidentCountriesPage.onPage()
      RemoveTaxResidentCountriesPage.selectYesAndContinue()

      And("Select No to add another tax resident countries")
      TaxResidentCountriesPage.selectNoAndContinue()

      Then("They are on the sponsor check answers page")
      SponsorCheckAnswersPage.checkPage()

      And("The summary shows the sponsor details")
      SponsorCheckAnswersPage
        .checkSummaryContains("Yes")
        .checkSummaryContains(TestData.sponsorName)
        .checkSummaryContains(TestData.sponsorGiin)
        .checkSummaryContains("United Kingdom")

      And("The tax resident countries card shows the resident country")
      SponsorCheckAnswersPage.checkTaxResidentContains(TestData.residentForTax)

      When("They continue from the check answers page")
      SponsorCheckAnswersPage.continueFromCheckAnswers()

      Then("They are returned to the manual send a report task list page")
      ManualSendAReportIndexPage.onPage()

    }

    Scenario("Sponsor journey - Address Non-UK", ManualSubmissionTests, SoloTests) {
      Given("The user have completed the sponsor journey up to Where are they based page")
      navigateToWhereAreTheyBased()

      When("The Sponsor is Non-UK based and continue")
      SponsorWhereBasedPage.selectNoAndContinue()

      And("The user entered the Non-UK address manually")
      SponsorNonUKAddressPage.enterAddressNonUK()

      And("Enter the sponsor resident for tax")
      ResidentForTaxPage.addResidentCountryForTax()

      And("Select No to add another tax resident countries")
      TaxResidentCountriesPage.selectNoAndContinue()

      Then("They are on the sponsor check answers page")
      SponsorCheckAnswersPage.checkPage()

      And("The summary shows the sponsor details")
      SponsorCheckAnswersPage
        .checkSummaryContains("Yes")
        .checkSummaryContains(TestData.sponsorName)
        .checkSummaryContains(TestData.sponsorGiin)
        .checkSummaryContains("Japan")

      And("The tax resident countries card shows the resident country")
      SponsorCheckAnswersPage.checkTaxResidentContains(TestData.residentForTax)

      When("They continue from the check answers page")
      SponsorCheckAnswersPage.continueFromCheckAnswers()

      Then("They are returned to the manual send a report task list page")
      ManualSendAReportIndexPage.onPage()
    }
  }
}
