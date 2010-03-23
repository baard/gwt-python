import cherrypy
import os.path
import time
from cherrypy import expose
from os import path

class Converter:
    _cp_config = {  
        'tools.staticdir.on' : True,
        'tools.staticdir.dir' : path.join(path.dirname(__file__), '..', 'war'),
    }

    @expose
    def fahr_to_celc(self, degrees):
        time.sleep(2)
        temp = (float(degrees) - 32) * 5 / 9
        return "%.01f" % temp

cherrypy.quickstart(Converter())
