<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<li>
	<a class="btn" href="/personal-area">
		<span><spring:message code="menu.personal"></spring:message></span>
	</a>
</li>
<li>
	<a class="btn" href="/find-train">
		<span>
			<spring:message code="menu.order"></spring:message>
		</span>
	</a>
</li>
<li>
	<a class="btn" href="/schedule">
		<span>
			<spring:message code="menu.schedule"></spring:message>
		</span>
	</a>
</li>

<li>
	<a class="btn" href="/logout">
		<span>
			<spring:message code="menu.logout"></spring:message>
		</span>
	</a>
</li>