<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<body>
    <header>
        <div class="container">
            <div class="clearfix" id="heading">
                <img src="/img/logo_white.png" alt="logo" class="logo">
                <nav>
                    <ul class="menu">

                        <security:authorize access="hasAuthority('USER')">
                            <jsp:include page="/WEB-INF/jsp/additional/menu-user.jsp" />
                        </security:authorize>
                        <security:authorize access="hasAuthority('ADMIN')">
                            <jsp:include page="/WEB-INF/jsp/additional/menu-admin.jsp" />
                        </security:authorize>
                        <security:authorize access="!isAuthenticated()">
                            <jsp:include page="/WEB-INF/jsp/additional/menu-login.jsp" />
                        </security:authorize>

                    </ul>
                </nav>
            </div>
            <div id="title_first">
                <h1>
                    <spring:message code="header.title"></spring:message>
                </h1>
                <p id="addition">
                    <spring:message code="header.qualifier"></spring:message>
                </p>
            </div>
        </div>
        <div class="separator"></div>
    </header>