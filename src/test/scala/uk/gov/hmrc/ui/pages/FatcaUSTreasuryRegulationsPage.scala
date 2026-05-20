package uk.gov.hmrc.ui.pages

object FatcaUSTreasuryRegulationsPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/elections/fatca/us-treasury-regulations"

  def checkPage(): this.type = {
    onPage(pageUrl)
    this
  }
}