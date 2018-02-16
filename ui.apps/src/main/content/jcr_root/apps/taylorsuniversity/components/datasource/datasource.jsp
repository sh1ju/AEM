<%@page session="false"
        import="org.apache.sling.api.resource.Resource,
                java.util.*,
                org.apache.sling.api.resource.ResourceUtil,
                org.apache.sling.api.resource.ValueMap,
                org.apache.sling.api.resource.ResourceResolver,
                org.apache.sling.api.resource.ResourceMetadata,
                org.apache.sling.api.wrappers.ValueMapDecorator,
                com.adobe.granite.ui.components.ds.DataSource,
                com.adobe.granite.ui.components.ds.EmptyDataSource,
                com.adobe.granite.ui.components.ds.SimpleDataSource,
                com.adobe.granite.ui.components.ds.ValueMapResource,
                com.day.cq.wcm.api.Page,
                com.google.gson.Gson,
                com.taylorsuniversity.models.bean.CourseClassificationsBeans,
                com.taylorsuniversity.models.bean.Programme,
                com.day.cq.wcm.api.PageManager,
                com.taylorsuniversity.utils.CoreUtils" %>
<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %>
<cq:defineObjects/><%

    // set fallback
    request.setAttribute(DataSource.class.getName(), EmptyDataSource.instance());
    ResourceResolver resolver = resource.getResourceResolver();

    log.error("Current Page:: {}", request.getParameter("item"));
    String currPath = request.getParameter("item");

    Page target = resolver.getResource(currPath).adaptTo(Page.class);
    log.error("Target Page:: {} ", target.getAbsoluteParent(3).getPath());
    target = target.getAbsoluteParent(3);

    //courseClassifications
    //Create an ArrayList to hold data
    List<Resource> newDataSourceList = new ArrayList<Resource>();
    List<String> stringList = CoreUtils.<List<String>>getMultiValueDropdownProperty(target, "courseClassifications");

    log.error("STRING SIZE::{}", stringList.size());

    Gson gson = new Gson();
    List<CourseClassificationsBeans> courseList = new ArrayList<CourseClassificationsBeans>();

    ValueMap vm = null;

    if (stringList.size() > 0) {
        for (String str : stringList) {
            courseList.add(gson.fromJson(str, CourseClassificationsBeans.class));
        }

        for (CourseClassificationsBeans course : courseList) {
            log.error("Level Of Study: ", course.getLevelOfStudy());
            vm = new ValueMapDecorator(new HashMap<String, Object>());
            String value = course.getLevelOfStudy();
            String text = course.getLevelOfStudy();

            vm.put("value", value);
            vm.put("text", text);

            newDataSourceList.add(new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm));

        }

        //Create a DataSource that is used to populate the drop-down control
        DataSource ds = new SimpleDataSource(newDataSourceList.iterator());
        request.setAttribute(DataSource.class.getName(), ds);
    } else {
        log.error("Course classifications empty:: {},", stringList.size());
        vm = new ValueMapDecorator(new HashMap<String, Object>());
        vm.put("value", "");
        vm.put("text", "No Course Classifications Configured");
        newDataSourceList.add(new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm));
    }

    //Create a DataSource that is used to populate the drop-down control
    DataSource ds = new SimpleDataSource(newDataSourceList.iterator());
    request.setAttribute(DataSource.class.getName(), ds);

%>