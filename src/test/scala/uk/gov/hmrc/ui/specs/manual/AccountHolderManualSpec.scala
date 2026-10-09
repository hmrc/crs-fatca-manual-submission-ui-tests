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
import uk.gov.hmrc.ui.pages.manual.accountholder.*
import uk.gov.hmrc.ui.specs.BaseSpec
import uk.gov.hmrc.ui.specs.tags.*
import uk.gov.hmrc.ui.utils.TestData

class AccountHolderManualSpec extends BaseSpec with ManualJourneyHelper {

  Feature("CRS/FATCA Manual - Account Holder individual name") {

    Scenario(
      "Account Holder - selecting Individual continues the journey - CRS",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
      navigateToTaskList("CRS")

      // TODO: Replace direct navigation below with the click-through once
      // ManualSendAReportIndexPage.clickAccountHolder() is implemented.
      When("They navigate directly to the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.goToPage()

      //      When("They open the account holder task")
      //      ManualSendAReportIndexPage.clickAccountHolder()

      Then("They are on the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.checkPage()

      When("They select Individual and continue")
      AccountHolderIndividualOrOrganisationPage.selectIndividualAndContinue()

      Then("They are on the individual name page")
      AccountHolderIndividualNamePage.checkPage()

      When("They enter a valid first and last name and continue")
      AccountHolderIndividualNamePage.enterNameAndContinue("Sarah", "Smith")

      Then("Navigate the account holder to have date of birth page")
      AccountHolderIndividualHaveDoB.checkPage()

      And("Select Yes or No on Date of birth for the account holder")
      AccountHolderIndividualHaveDoB.selectYesAndContinue()

      Then("They enter a valid date of birth and continue")
      AccountHolderIndividualD0B.enterDateOfBirth()

      Then("They are on the have place of birth page")
      IndividualHavePlaceOfBirthPage.checkPage()

      When("They select Yes and continue")
      IndividualHavePlaceOfBirthPage.selectYesAndContinue()

      Then("They lands on individual place of birth page")
      AccountHolderIndividualPlaceOfBirthPage.checkPage()

      And("They enter the Individual place of birth")
      AccountHolderIndividualPlaceOfBirthPage.enterPlaceOfBirthAndContinue("city", "Region", "Japan")

      Then("They are on the where are they based page")
      AccountHolderWhereAreTheyBasedPage.checkPage()

      When("They select No and continue")
      AccountHolderWhereAreTheyBasedPage.selectNoAndContinue()

      Then("They lands on addressNon-UK page")
      AccountHolderAddressNonUKPage.checkPage()

      When("They enter the non UK address and continue")
      AccountHolderAddressNonUKPage.enterAddressNonUK()

    }

    Scenario(
      "Account Holder - selecting Individual continues the journey - FATCA",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")

      // TODO: Replace direct navigation below with the click-through once
      // ManualSendAReportIndexPage.clickAccountHolder() is implemented.
      When("They navigate directly to the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.goToPage()

      //      When("They open the account holder task")
      //      ManualSendAReportIndexPage.clickAccountHolder()

      Then("They are on the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.checkPage()

      When("They select Individual and continue")
      AccountHolderIndividualOrOrganisationPage.selectIndividualAndContinue()

      Then("They are on the individual name page")
      AccountHolderIndividualNamePage.checkPage()

      When("They enter a valid first and last name and continue")
      AccountHolderIndividualNamePage.enterNameAndContinue("Sarah", "Smith")

      Then("Navigate the account holder to have date of birth page")
      AccountHolderIndividualHaveDoB.checkPage()

      And("Select Yes or No on Date of birth for the account holder")
      AccountHolderIndividualHaveDoB.selectYesAndContinue()

      Then("They enter a valid date of birth and continue")
      AccountHolderIndividualD0B.enterDateOfBirth()

      Then("They are on the have place of birth page")
      IndividualHavePlaceOfBirthPage.checkPage()

      When("They select No and continue")
      IndividualHavePlaceOfBirthPage.selectNoAndContinue()

      Then("They are on the where are they based page")
      AccountHolderWhereAreTheyBasedPage.checkPage()

      When("They select Yes and continue")
      AccountHolderWhereAreTheyBasedPage.selectYesAndContinue()

      Then("They are on UK postcode page")
      AccountHolderUKPostcodePage.checkPage()

      When("They enter the postcode and search for the address")
      AccountHolderUKPostcodePage.enterPostcodeAndContinueForAddress(TestData.postcodeMultipleAddress)

      Then("They lands on the multiple addresses page for the given postcode")
      AccountHolderSelectAddressPage.checkPage()

      When("They select the enter address manually link")
      AccountHolderSelectAddressPage.enterAddressManually()

      Then("They will take to address-uk page to enter the address manually")
      AccountHolderAddressUKPage.checkPage()

      And("They can enter the address manually")
      AccountHolderAddressUKPage.enterAddressUK()
    }

    Scenario(
      "Account Holder - selecting Organisation continues the journey - CRS/FATCA",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
      navigateToTaskList("FATCA")

      // TODO: Replace direct navigation below with the click-through once
      // ManualSendAReportIndexPage.clickAccountHolder() is implemented.
      When("They navigate directly to the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.goToPage()

      //      When("They open the account holder task")
      //      ManualSendAReportIndexPage.clickAccountHolder()

      Then("They are on the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.checkPage()

      When("They select Organisation and continue")
      AccountHolderIndividualOrOrganisationPage.selectOrganisationAndContinue()

      And("They provide the organisation Name and continue")
      AccountHolderOrganisationNamePage.enterFatcaAccountHolderName()

      Then("They are on organisation FATCA type page")
      AccountHolderOrgFatcaTypePage.checkPage()

      And("They selects the organisation FATCA type and continue")
      AccountHolderOrgFatcaTypePage.selectAccountHolderType("specifiedPerson")

      Then("They will lands on where are they based page")
      AccountHolderWhereAreTheyBasedPage.checkPage()

      When("They select no on where are they based and continue ")
      AccountHolderWhereAreTheyBasedPage.selectNoAndContinue()

      Then("They lands on address non uk page ")
      AccountHolderAddressNonUKPage.checkPage()

      And("They provides the address non uk and continue")
      AccountHolderAddressNonUKPage.enterAddressNonUK()
    }

    Scenario(
      "Account Holder - selecting Organisation continues the journey - CRS",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("ThirdFI")
      navigateToTaskList("CRS")

      // TODO: Replace direct navigation below with the click-through once
      // ManualSendAReportIndexPage.clickAccountHolder() is implemented.
      When("They navigate directly to the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.goToPage()

      //      When("They open the account holder task")
      //      ManualSendAReportIndexPage.clickAccountHolder()

      Then("They are on the individual or organisation page")
      AccountHolderIndividualOrOrganisationPage.checkPage()

      When("They select Organisation and continue")
      AccountHolderIndividualOrOrganisationPage.selectOrganisationAndContinue()

      And("They provide the organisation Name and continue")
      AccountHolderOrganisationNamePage.enterCrsAccountHolderName()

      Then("They are on organisation CRS type page")
      AccountHolderOrgCrsTypePage.checkPage()

      And("They selects the organisation CRS type and continue")
      AccountHolderOrgCrsTypePage.selectAccountHolderType("crsReportable")

      Then("They will lands on where are they based page")
      AccountHolderWhereAreTheyBasedPage.checkPage()

      When("They select yes on where are they based and continue ")
      AccountHolderWhereAreTheyBasedPage.selectYesAndContinue()

      Then("They lands on UK postcode page")
      AccountHolderUKPostcodePage.checkPage()

      When("They enter the postcode and search for the address")
      AccountHolderUKPostcodePage.enterPostcodeAndContinueForAddress(TestData.postcodeSingleAddress)

      Then("They lands on Is this your address page")
      AccountHolderIsThisTheAddressPage.checkPage()

      When("They select the address Yes and continue")
      AccountHolderIsThisTheAddressPage.selectYesAndContinue()
    }
  }
}
