package com.epam.lab.rto.manager;

import com.epam.lab.rto.dto.Route;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Service
@SessionScope
public class RouteManager {

    private List<String> routeWay;

    private Route currentRoute;

    public List<String> getRouteWay() {
        return routeWay;
    }

    public void setRouteWay(List<String> routeWay) {
        this.routeWay = routeWay;
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }
}
