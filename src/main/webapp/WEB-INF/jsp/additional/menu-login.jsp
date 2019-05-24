<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav>
    <ul class="menu">
        <li>
            <a class="btn" href="/login">
                <span>
                    <spring:message code="menu.login"></spring:message>
                </span>
            </a>
        </li>
        <li>
            <a class="btn" href="/login/registration">
                <span>
                    <spring:message code="menu.registration"></spring:message>
                </span>
            </a>
        </li>
    </ul>
</nav>