package es.spin21.bluu.streamer.utils;

import es.spin21.bluu.entities.Account;
import es.spin21.bluu.entities.Node;
import es.spin21.bluu.entities.Route;
import es.spin21.bluu.entities.RouteType;
import es.spin21.bluu.utils.EntityManagerHelper;

import javax.persistence.EntityManager;
import java.util.List;

public class Finder {
    private EntityManager em;

    public Account brandByAlias(String alias) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        List<Account> brands = em.createNamedQuery("Account.findAccountByAlias")
                .setParameter("alias", alias)
                .setMaxResults(1)
                .getResultList();

        if (brands == null || brands.isEmpty()) return null;
        return brands.get(0);
    }

    public Account unknownAccount() {
        EntityManager em = EntityManagerHelper.getEntityManager();
        List<Account> accounts = em.createNamedQuery("Account.findUnknownAccount")
                .setMaxResults(1)
                .getResultList();

        if (accounts == null || accounts.isEmpty()) return null;
        return accounts.get(0);
    }

    public Node nodeByAccountAndName(Account account, String name) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        List<Node> nodes = em.createNamedQuery("Node.findNodeByAccountAndName")
                .setParameter("accountId", account.getId())
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultList();

        if (nodes == null || nodes.isEmpty()) return null;
        return nodes.get(0);
    }

    public Node unknownNode() {
        EntityManager em = EntityManagerHelper.getEntityManager();
        List<Node> nodes = em.createNamedQuery("Node.findUnknownNode")
                .setMaxResults(1)
                .getResultList();

        if (nodes == null || nodes.isEmpty()) return null;
        return nodes.get(0);
    }

    public Node nodeByAccountAndCcc(Account account, String ccc) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        List<Node> nodes = em.createNamedQuery("Node.findNodeByAccountAndCcc")
                .setParameter("accountId", account.getId())
                .setParameter("ccc", ccc)
                .setMaxResults(1)
                .getResultList();

        if (nodes == null || nodes.isEmpty()) return null;
        return nodes.get(0);
    }

    public Account accountByName(String name) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        List<Account> brands = em.createNamedQuery("Account.findAccountByName")
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultList();

        if (brands == null || brands.isEmpty()) return null;
        return brands.get(0);
    }

    public Account accountByOperatorId(String operatorId) {
        if (operatorId == null || operatorId.equals("")) return null;

        EntityManager em = EntityManagerHelper.getEntityManager();
        List<Account> accounts = em.createNamedQuery("Account.findAccountByOperatorId")
                .setParameter("operatorId", operatorId)
                .setMaxResults(1)
                .getResultList();

        if (accounts == null || accounts.isEmpty()) return null;
        return accounts.get(0);
    }

    public RouteType routeTypeByAlias(String alias) {
        if (alias == null || alias.equals("")) return null;

        EntityManager em = EntityManagerHelper.getEntityManager();
        List<RouteType> routeTypes = em.createNamedQuery("RouteType.findTypeByAlias")
                .setParameter("alias", alias)
                .setMaxResults(1)
                .getResultList();

        if (routeTypes == null || routeTypes.isEmpty()) return null;
        return routeTypes.get(0);
    }

    public Node nodeByAlias(String alias) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        List<Node> nodes = em.createNamedQuery("Node.findNodeByAlias")
                .setParameter("alias", alias)
                .setMaxResults(1)
                .getResultList();

        if (nodes == null || nodes.isEmpty()) return null;
        return nodes.get(0);
    }

    public Route routeByAdnumAndNode(String adnum, Integer nodeId) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        List<Route> routes = em.createNamedQuery("Route.findRouteByAdnumAndNode")
                .setParameter("adnum", adnum)
                .setParameter("nodeId", nodeId)
                .setMaxResults(1)
                .getResultList();

        if (routes == null || routes.isEmpty()) return null;
        return routes.get(0);
    }

    public Route routeByAdnumAndNodeAlias(String adnum, String nodeAlias) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        List<Route> routes = em.createNamedQuery("Route.findRouteByAdnumAndNodeAlias")
                .setParameter("adnum", adnum)
                .setParameter("alias", nodeAlias)
                .setMaxResults(1)
                .getResultList();

        if (routes == null || routes.isEmpty()) return null;
        return routes.get(0);
    }

}
