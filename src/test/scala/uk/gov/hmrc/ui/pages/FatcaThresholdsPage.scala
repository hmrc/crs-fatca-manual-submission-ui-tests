package uk.gov.hmrc.ui.pages

import uk.gov.hmrc.ui.pages.FatcaUSTreasuryRegulationsPage.{onPage, pageUrl}

object FatcaThresholdsPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/elections/fatca/thresholds"

  def checkPage(): this.type = {
    onPage(pageUrl)
    this
  }
}
