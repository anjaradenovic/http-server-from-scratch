# http-server-from-scratch
Basic http server written from scratch in Java
1. [Introduction](#introduction)
2. [API](#paragraph1)
3. [Static resorces](#paragraph2)

## Introduction <a name="introduction"></a>
Basic http server written from scratch in Java. It can serve JSON responses,
static files or generate html files from template.

## API <a name="paragraph1"></a>
The following endpoints are available:

```
GET /zdravo/{some_name}
``` 
Returns an HTML page from template, injecting content from request, e.g: *Hello Anja!*


```
GET /api
```
Returns JSON response

```
{ "temperature":24, "humidity":94, "pressure":998 }
```

## Static resorces <a name="paragraph2"></a>

* ```GET /index.html```
* ```GET /about.html```
* ```GET /contact.html```
* ```GET /pricing.html```

Returns content of static resorces. Any html/css/js static files that are
added to the /static folder will be served by their name.