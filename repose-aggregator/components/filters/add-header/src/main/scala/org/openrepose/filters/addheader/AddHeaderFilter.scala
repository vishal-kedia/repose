package org.openrepose.filters.addheader

import javax.inject.{Inject, Named}
import javax.servlet._

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.openrepose.commons.config.manager.UpdateListener
import org.openrepose.core.filter.FilterConfigHelper
import org.openrepose.core.filter.logic.impl.FilterLogicHandlerDelegate
import org.openrepose.core.services.config.ConfigurationService
import org.openrepose.filters.addheader.config.AddHeadersConfig

@Named
class AddHeaderFilter @Inject() (configurationService: ConfigurationService) extends Filter with LazyLogging {

  private final val DEFAULT_CONFIG = "add-header.cfg.xml"

  private var config: String = _
  private var handlerFactory: AddHeaderHandlerFactory = _

  override def init(filterConfig: FilterConfig): Unit = {
    config = new FilterConfigHelper(filterConfig).getFilterConfig(DEFAULT_CONFIG)
    logger.info(s"Initializing AddHeaderFilter using config $config")

    //Spring hack -- as copied from RackspaceAuthUserFilter
    handlerFactory = new AddHeaderHandlerFactory()

    val xsdURL = getClass.getResource("/META-INF/schema/config/add-header.xsd")
    configurationService.subscribeTo(filterConfig.getFilterName,
      config,
      xsdURL,
      handlerFactory.asInstanceOf[UpdateListener[AddHeadersConfig]],
      classOf[AddHeadersConfig])
  }

  override def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {
    new FilterLogicHandlerDelegate(request, response, chain).doFilter(handlerFactory.newHandler)
  }

  override def destroy(): Unit = {
    configurationService.unsubscribeFrom(config, handlerFactory)
  }
}
