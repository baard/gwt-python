import cherrypy
from cherrypy import expose

class Converter:
    _cp_config = {'tools.staticdir.on' : True,
                  'tools.staticdir.dir' : '/home/baard/external/Projects/gwt-sample/gwt-sample/war',
   }

    @expose
    def fahr_to_celc(self, degrees):
        temp = (float(degrees) - 32) * 5 / 9
        return "%.01f" % temp

cherrypy.quickstart(Converter())
