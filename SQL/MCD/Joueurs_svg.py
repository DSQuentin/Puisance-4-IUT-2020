#!/usr/bin/env python
# encoding: utf-8
# Généré par Mocodo 2.3.7 le Wed, 25 Mar 2020 03:56:29

from __future__ import division
from math import hypot

import time, codecs

import json

with codecs.open('Joueurs_geo.json') as f:
    geo = json.loads(f.read())
(width,height) = geo.pop('size')
for (name, l) in geo.items(): globals()[name] = dict(l)
card_max_width = 22
card_max_height = 15
card_margin = 5
arrow_width = 12
arrow_half_height = 6
arrow_axis = 8
card_baseline = 3

def cmp(x, y):
    return (x > y) - (x < y)

def offset(x, y):
    return (x + card_margin, y - card_baseline - card_margin)

def line_intersection(ex, ey, w, h, ax, ay):
    if ax == ex:
        return (ax, ey + cmp(ay, ey) * h)
    if ay == ey:
        return (ex + cmp(ax, ex) * w, ay)
    x = ex + cmp(ax, ex) * w
    y = ey + (ay-ey) * (x-ex) / (ax-ex)
    if abs(y-ey) > h:
        y = ey + cmp(ay, ey) * h
        x = ex + (ax-ex) * (y-ey) / (ay-ey)
    return (x, y)

def straight_leg_factory(ex, ey, ew, eh, ax, ay, aw, ah, cw, ch):
    
    def card_pos(twist, shift):
        compare = (lambda x1_y1: x1_y1[0] < x1_y1[1]) if twist else (lambda x1_y1: x1_y1[0] <= x1_y1[1])
        diagonal = hypot(ax-ex, ay-ey)
        correction = card_margin * 1.4142 * (1 - abs(abs(ax-ex) - abs(ay-ey)) / diagonal) - shift
        (xg, yg) = line_intersection(ex, ey, ew, eh + ch, ax, ay)
        (xb, yb) = line_intersection(ex, ey, ew + cw, eh, ax, ay)
        if compare((xg, xb)):
            if compare((xg, ex)):
                if compare((yb, ey)):
                    return (xb - correction, yb)
                return (xb - correction, yb + ch)
            if compare((yb, ey)):
                return (xg, yg + ch - correction)
            return (xg, yg + correction)
        if compare((xb, ex)):
            if compare((yb, ey)):
                return (xg - cw, yg + ch - correction)
            return (xg - cw, yg + correction)
        if compare((yb, ey)):
            return (xb - cw + correction, yb)
        return (xb - cw + correction, yb + ch)
    
    def arrow_pos(direction, ratio):
        (x0, y0) = line_intersection(ex, ey, ew, eh, ax, ay)
        (x1, y1) = line_intersection(ax, ay, aw, ah, ex, ey)
        if direction == "<":
            (x0, y0, x1, y1) = (x1, y1, x0, y0)
        (x, y) = (ratio * x0 + (1 - ratio) * x1, ratio * y0 + (1 - ratio) * y1)
        return (x, y, x1 - x0, y0 - y1)
    
    straight_leg_factory.card_pos = card_pos
    straight_leg_factory.arrow_pos = arrow_pos
    return straight_leg_factory


def curved_leg_factory(ex, ey, ew, eh, ax, ay, aw, ah, cw, ch, spin):
    
    def bisection(predicate):
        (a, b) = (0, 1)
        while abs(b - a) > 0.0001:
            m = (a + b) / 2
            if predicate(bezier(m)):
                a = m
            else:
                b = m
        return m
    
    def intersection(left, top, right, bottom):
       (x, y) = bezier(bisection(lambda p: left <= p[0] <= right and top <= p[1] <= bottom))
       return (int(round(x)), int(round(y)))
    
    def card_pos(shift):
        diagonal = hypot(ax-ex, ay-ey)
        correction = card_margin * 1.4142 * (1 - abs(abs(ax-ex) - abs(ay-ey)) / diagonal)
        (top, bot) = (ey - eh, ey + eh)
        (TOP, BOT) = (top - ch, bot + ch)
        (lef, rig) = (ex - ew, ex + ew)
        (LEF, RIG) = (lef - cw, rig + cw)
        (xr, yr) = intersection(LEF, TOP, RIG, BOT)
        (xg, yg) = intersection(lef, TOP, rig, BOT)
        (xb, yb) = intersection(LEF, top, RIG, bot)
        if spin > 0:
            if (yr == BOT and xr <= rig) or (xr == LEF and yr >= bot):
                return (max(x for (x, y) in ((xr, yr), (xg, yg), (xb, yb)) if y >= bot) - correction + shift, bot + ch)
            if (xr == RIG and yr >= top) or yr == BOT:
                return (rig, min(y for (x, y) in ((xr, yr), (xg, yg), (xb, yb)) if x >= rig) + correction + shift)
            if (yr == TOP and xr >= lef) or xr == RIG:
                return (min(x for (x, y) in ((xr, yr), (xg, yg), (xb, yb)) if y <= top) + correction + shift - cw, TOP + ch)
            return (LEF, max(y for (x, y) in ((xr, yr), (xg, yg), (xb, yb)) if x <= lef) - correction + shift + ch)
        if (yr == BOT and xr >= lef) or (xr == RIG and yr >= bot):
            return (min(x for (x, y) in ((xr, yr), (xg, yg), (xb, yb)) if y >= bot) + correction + shift - cw, bot + ch)
        if xr == RIG or (yr == TOP and xr >= rig):
            return (rig, max(y for (x, y) in ((xr, yr), (xg, yg), (xb, yb)) if x >= rig) - correction + shift + ch)
        if yr == TOP or (xr == LEF and yr <= top):
            return (max(x for (x, y) in ((xr, yr), (xg, yg), (xb, yb)) if y <= top) - correction + shift, TOP + ch)
        return (LEF, min(y for (x, y) in ((xr, yr), (xg, yg), (xb, yb)) if x <= lef) + correction + shift)
    
    def arrow_pos(direction, ratio):
        t0 = bisection(lambda p: abs(p[0] - ax) > aw or abs(p[1] - ay) > ah)
        t3 = bisection(lambda p: abs(p[0] - ex) < ew and abs(p[1] - ey) < eh)
        if direction == "<":
            (t0, t3) = (t3, t0)
        tc = t0 + (t3 - t0) * ratio
        (xc, yc) = bezier(tc)
        (x, y) = derivate(tc)
        if direction == "<":
            (x, y) = (-x, -y)
        return (xc, yc, x, -y)
    
    diagonal = hypot(ax - ex, ay - ey)
    (x, y) = line_intersection(ex, ey, ew + cw / 2, eh + ch / 2, ax, ay)
    k = (cw *  abs((ay - ey) / diagonal) + ch * abs((ax - ex) / diagonal))
    (x, y) = (x - spin * k * (ay - ey) / diagonal, y + spin * k * (ax - ex) / diagonal)
    (hx, hy) = (2 * x - (ex + ax) / 2, 2 * y - (ey + ay) / 2)
    (x1, y1) = (ex + (hx - ex) * 2 / 3, ey + (hy - ey) * 2 / 3)
    (x2, y2) = (ax + (hx - ax) * 2 / 3, ay + (hy - ay) * 2 / 3)
    (kax, kay) = (ex - 2 * hx + ax, ey - 2 * hy + ay)
    (kbx, kby) = (2 * hx - 2 * ex, 2 * hy - 2 * ey)
    bezier = lambda t: (kax*t*t + kbx*t + ex, kay*t*t + kby*t + ey)
    derivate = lambda t: (2*kax*t + kbx, 2*kay*t + kby)
    
    curved_leg_factory.points = (ex, ey, x1, y1, x2, y2, ax, ay)
    curved_leg_factory.card_pos = card_pos
    curved_leg_factory.arrow_pos = arrow_pos
    return curved_leg_factory


def upper_round_rect(x, y, w, h, r):
    return " ".join([str(x) for x in ["M", x + w - r, y, "a", r, r, 90, 0, 1, r, r, "V", y + h, "h", -w, "V", y + r, "a", r, r, 90, 0, 1, r, -r]])

def lower_round_rect(x, y, w, h, r):
    return " ".join([str(x) for x in ["M", x + w, y, "v", h - r, "a", r, r, 90, 0, 1, -r, r, "H", x + r, "a", r, r, 90, 0, 1, -r, -r, "V", y, "H", w]])

def arrow(x, y, a, b):
    c = hypot(a, b)
    (cos, sin) = (a / c, b / c)
    return " ".join([str(x) for x in [ "M", x, y, "L", x + arrow_width * cos - arrow_half_height * sin, y - arrow_half_height * cos - arrow_width * sin, "L", x + arrow_axis * cos, y - arrow_axis * sin, "L", x + arrow_width * cos + arrow_half_height * sin, y + arrow_half_height * cos - arrow_width * sin, "Z"]])

def safe_print_for_PHP(s):
    try:
        print(s)
    except UnicodeEncodeError:
        print(s.encode("utf8"))


lines = '<?xml version="1.0" standalone="no"?>\n<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN"\n"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">'
lines += '\n\n<svg width="%s" height="%s" view_box="0 0 %s %s"\nxmlns="http://www.w3.org/2000/svg"\nxmlns:link="http://www.w3.org/1999/xlink">' % (width,height,width,height)
lines += u'\\n\\n<desc>Généré par Mocodo 2.3.7 le %s</desc>' % time.strftime("%a, %d %b %Y %H:%M:%S", time.localtime())
lines += '\n\n<rect id="frame" x="0" y="0" width="%s" height="%s" fill="%s" stroke="none" stroke-width="0"/>' % (width,height,colors['background_color'] if colors['background_color'] else "none")

lines += u"""\n\n<!-- Association PARTICIPER -->"""
(x,y) = (cx[u"PARTICIPER"],cy[u"PARTICIPER"])
(ex,ey) = (cx[u"PARTIE"],cy[u"PARTIE"])
leg=straight_leg_factory(ex,ey,41,93,x,y,44,42,21+2*card_margin,15+2*card_margin)
lines += u"""\n<line x1="%(ex)s" y1="%(ey)s" x2="%(ax)s" y2="%(ay)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'ex': ex, 'ey': ey, 'ax': x, 'ay': y, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(False,shift[u"PARTICIPER,PARTIE"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12">1,1</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
(ex,ey) = (cx[u"JOUEUR"],cy[u"JOUEUR"])
leg=curved_leg_factory(ex,ey,35,93,x,y,44,42,22+2*card_margin,15+2*card_margin,-1.0)
(x0, y0, x1, y1, x2, y2, x3, y3)=leg.points
lines += u"""\n<path d="M%(x0)s %(y0)s C %(x1)s %(y1)s %(x2)s %(y2)s %(x3)s %(y3)s" fill="none" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x0': x0, 'y0': y0, 'x1': x1, 'y1': y1, 'x2': x2, 'y2': y2, 'x3': x3, 'y3': y3, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(shift[u"PARTICIPER,JOUEUR,-1.0"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12" onmouseover="show(evt,'joueur1')" onmouseout="hide(evt)" style="cursor: pointer;">0,N</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
path=arrow(*leg.arrow_pos(">",ratio[u"PARTICIPER,JOUEUR,-1.0"]))
lines += u"""\n<path d="%(path)s" fill="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'stroke_color': colors['leg_stroke_color']}
(ex,ey) = (cx[u"JOUEUR"],cy[u"JOUEUR"])
leg=curved_leg_factory(ex,ey,35,93,x,y,44,42,22+2*card_margin,15+2*card_margin,1.0)
(x0, y0, x1, y1, x2, y2, x3, y3)=leg.points
lines += u"""\n<path d="M%(x0)s %(y0)s C %(x1)s %(y1)s %(x2)s %(y2)s %(x3)s %(y3)s" fill="none" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x0': x0, 'y0': y0, 'x1': x1, 'y1': y1, 'x2': x2, 'y2': y2, 'x3': x3, 'y3': y3, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(shift[u"PARTICIPER,JOUEUR,1.0"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12" onmouseover="show(evt,'joueur2')" onmouseout="hide(evt)" style="cursor: pointer;">0,N</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
path=arrow(*leg.arrow_pos(">",ratio[u"PARTICIPER,JOUEUR,1.0"]))
lines += u"""\n<path d="%(path)s" fill="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'stroke_color': colors['leg_stroke_color']}
lines += u"""\n<g id="association-PARTICIPER">""" % {}
path = upper_round_rect(-44+x,-42+y,88,25,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_cartouche_color'], 'stroke_color': colors['association_cartouche_color']}
path = lower_round_rect(-44+x,-17.0+y,88,59,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_color'], 'stroke_color': colors['association_color']}
lines += u"""\n	<rect x="%(x)s" y="%(y)s" width="88" height="84" fill="%(color)s" rx="14" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x': -44+x, 'y': -42+y, 'color': colors['transparent_color'], 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -44+x, 'y0': -17+y, 'x1': 44+x, 'y1': -17+y, 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">PARTICIPER</text>""" % {'x': -37+x, 'y': -24.3+y, 'text_color': colors['association_cartouche_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">id</text>""" % {'x': -37+x, 'y': 0.8+y, 'text_color': colors['association_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">pseudo1</text>""" % {'x': -37+x, 'y': 17.8+y, 'text_color': colors['association_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">pseudo2</text>""" % {'x': -37+x, 'y': 34.8+y, 'text_color': colors['association_attribute_text_color']}
lines += u"""\n</g>""" % {}

lines += u"""\n\n<!-- Association RECEVOIRInv -->"""
(x,y) = (cx[u"RECEVOIRInv"],cy[u"RECEVOIRInv"])
(ex,ey) = (cx[u"JOUEUR"],cy[u"JOUEUR"])
leg=straight_leg_factory(ex,ey,35,93,x,y,49,25,22+2*card_margin,15+2*card_margin)
lines += u"""\n<line x1="%(ex)s" y1="%(ey)s" x2="%(ax)s" y2="%(ay)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'ex': ex, 'ey': ey, 'ax': x, 'ay': y, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(False,shift[u"RECEVOIRInv,JOUEUR"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12">1,N</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
(ex,ey) = (cx[u"INVITATION"],cy[u"INVITATION"])
leg=straight_leg_factory(ex,ey,43,68,x,y,49,25,21+2*card_margin,15+2*card_margin)
lines += u"""\n<line x1="%(ex)s" y1="%(ey)s" x2="%(ax)s" y2="%(ay)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'ex': ex, 'ey': ey, 'ax': x, 'ay': y, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(False,shift[u"RECEVOIRInv,INVITATION"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12">1,1</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
lines += u"""\n<g id="association-RECEVOIRInv">""" % {}
path = upper_round_rect(-49+x,-25+y,98,25,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_cartouche_color'], 'stroke_color': colors['association_cartouche_color']}
path = lower_round_rect(-49+x,0.0+y,98,25,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_color'], 'stroke_color': colors['association_color']}
lines += u"""\n	<rect x="%(x)s" y="%(y)s" width="98" height="50" fill="%(color)s" rx="14" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x': -49+x, 'y': -25+y, 'color': colors['transparent_color'], 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -49+x, 'y0': 0+y, 'x1': 49+x, 'y1': 0+y, 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">RECEVOIRInv</text>""" % {'x': -42+x, 'y': -7.3+y, 'text_color': colors['association_cartouche_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">lu</text>""" % {'x': -42+x, 'y': 17.8+y, 'text_color': colors['association_attribute_text_color']}
lines += u"""\n</g>""" % {}

lines += u"""\n\n<!-- Association EMETTREInv -->"""
(x,y) = (cx[u"EMETTREInv"],cy[u"EMETTREInv"])
(ex,ey) = (cx[u"JOUEUR"],cy[u"JOUEUR"])
leg=straight_leg_factory(ex,ey,35,93,x,y,46,25,22+2*card_margin,15+2*card_margin)
lines += u"""\n<line x1="%(ex)s" y1="%(ey)s" x2="%(ax)s" y2="%(ay)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'ex': ex, 'ey': ey, 'ax': x, 'ay': y, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(False,shift[u"EMETTREInv,JOUEUR"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12">1,N</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
(ex,ey) = (cx[u"INVITATION"],cy[u"INVITATION"])
leg=straight_leg_factory(ex,ey,43,68,x,y,46,25,21+2*card_margin,15+2*card_margin)
lines += u"""\n<line x1="%(ex)s" y1="%(ey)s" x2="%(ax)s" y2="%(ay)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'ex': ex, 'ey': ey, 'ax': x, 'ay': y, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(False,shift[u"EMETTREInv,INVITATION"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12">1,1</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
lines += u"""\n<g id="association-EMETTREInv">""" % {}
path = upper_round_rect(-46+x,-25+y,92,25,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_cartouche_color'], 'stroke_color': colors['association_cartouche_color']}
path = lower_round_rect(-46+x,0.0+y,92,25,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_color'], 'stroke_color': colors['association_color']}
lines += u"""\n	<rect x="%(x)s" y="%(y)s" width="92" height="50" fill="%(color)s" rx="14" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x': -46+x, 'y': -25+y, 'color': colors['transparent_color'], 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -46+x, 'y0': 0+y, 'x1': 46+x, 'y1': 0+y, 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">EMETTREInv</text>""" % {'x': -39+x, 'y': -7.3+y, 'text_color': colors['association_cartouche_text_color']}
lines += u"""\n</g>""" % {}

lines += u"""\n\n<!-- Association EMETTRE -->"""
(x,y) = (cx[u"EMETTRE"],cy[u"EMETTRE"])
(ex,ey) = (cx[u"JOUEUR"],cy[u"JOUEUR"])
leg=straight_leg_factory(ex,ey,35,93,x,y,36,25,22+2*card_margin,15+2*card_margin)
lines += u"""\n<line x1="%(ex)s" y1="%(ey)s" x2="%(ax)s" y2="%(ay)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'ex': ex, 'ey': ey, 'ax': x, 'ay': y, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(False,shift[u"EMETTRE,JOUEUR"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12">1,N</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
(ex,ey) = (cx[u"MESSAGES"],cy[u"MESSAGES"])
leg=straight_leg_factory(ex,ey,42,68,x,y,36,25,21+2*card_margin,15+2*card_margin)
lines += u"""\n<line x1="%(ex)s" y1="%(ey)s" x2="%(ax)s" y2="%(ay)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'ex': ex, 'ey': ey, 'ax': x, 'ay': y, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(False,shift[u"EMETTRE,MESSAGES"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12">1,1</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
lines += u"""\n<g id="association-EMETTRE">""" % {}
path = upper_round_rect(-36+x,-25+y,72,25,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_cartouche_color'], 'stroke_color': colors['association_cartouche_color']}
path = lower_round_rect(-36+x,0.0+y,72,25,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_color'], 'stroke_color': colors['association_color']}
lines += u"""\n	<rect x="%(x)s" y="%(y)s" width="72" height="50" fill="%(color)s" rx="14" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x': -36+x, 'y': -25+y, 'color': colors['transparent_color'], 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -36+x, 'y0': 0+y, 'x1': 36+x, 'y1': 0+y, 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">EMETTRE</text>""" % {'x': -29+x, 'y': -7.3+y, 'text_color': colors['association_cartouche_text_color']}
lines += u"""\n</g>""" % {}

lines += u"""\n\n<!-- Association ÊTREAMISAVEC -->"""
(x,y) = (cx[u"ÊTREAMISAVEC"],cy[u"ÊTREAMISAVEC"])
(ex,ey) = (cx[u"JOUEUR"],cy[u"JOUEUR"])
leg=curved_leg_factory(ex,ey,35,93,x,y,55,25,22+2*card_margin,15+2*card_margin,-1.0)
(x0, y0, x1, y1, x2, y2, x3, y3)=leg.points
lines += u"""\n<path d="M%(x0)s %(y0)s C %(x1)s %(y1)s %(x2)s %(y2)s %(x3)s %(y3)s" fill="none" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x0': x0, 'y0': y0, 'x1': x1, 'y1': y1, 'x2': x2, 'y2': y2, 'x3': x3, 'y3': y3, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(shift[u"ÊTREAMISAVEC,JOUEUR,-1.0"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12" onmouseover="show(evt,'joueur')" onmouseout="hide(evt)" style="cursor: pointer;">0,N</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
path=arrow(*leg.arrow_pos(">",ratio[u"ÊTREAMISAVEC,JOUEUR,-1.0"]))
lines += u"""\n<path d="%(path)s" fill="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'stroke_color': colors['leg_stroke_color']}
(ex,ey) = (cx[u"JOUEUR"],cy[u"JOUEUR"])
leg=curved_leg_factory(ex,ey,35,93,x,y,55,25,22+2*card_margin,15+2*card_margin,1.0)
(x0, y0, x1, y1, x2, y2, x3, y3)=leg.points
lines += u"""\n<path d="M%(x0)s %(y0)s C %(x1)s %(y1)s %(x2)s %(y2)s %(x3)s %(y3)s" fill="none" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x0': x0, 'y0': y0, 'x1': x1, 'y1': y1, 'x2': x2, 'y2': y2, 'x3': x3, 'y3': y3, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(shift[u"ÊTREAMISAVEC,JOUEUR,1.0"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12">0,N</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
lines += u"""\n<g id="association-ÊTREAMISAVEC">""" % {}
path = upper_round_rect(-55+x,-25+y,110,25,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_cartouche_color'], 'stroke_color': colors['association_cartouche_color']}
path = lower_round_rect(-55+x,0.0+y,110,25,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_color'], 'stroke_color': colors['association_color']}
lines += u"""\n	<rect x="%(x)s" y="%(y)s" width="110" height="50" fill="%(color)s" rx="14" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x': -55+x, 'y': -25+y, 'color': colors['transparent_color'], 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -55+x, 'y0': 0+y, 'x1': 55+x, 'y1': 0+y, 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">ÊTREAMISAVEC</text>""" % {'x': -48+x, 'y': -7.3+y, 'text_color': colors['association_cartouche_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">pseudo2</text>""" % {'x': -48+x, 'y': 17.8+y, 'text_color': colors['association_attribute_text_color']}
lines += u"""\n</g>""" % {}

lines += u"""\n\n<!-- Association RECEVOIR -->"""
(x,y) = (cx[u"RECEVOIR"],cy[u"RECEVOIR"])
(ex,ey) = (cx[u"JOUEUR"],cy[u"JOUEUR"])
leg=straight_leg_factory(ex,ey,35,93,x,y,39,25,22+2*card_margin,15+2*card_margin)
lines += u"""\n<line x1="%(ex)s" y1="%(ey)s" x2="%(ax)s" y2="%(ay)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'ex': ex, 'ey': ey, 'ax': x, 'ay': y, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(False,shift[u"RECEVOIR,JOUEUR"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12">1,N</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
(ex,ey) = (cx[u"MESSAGES"],cy[u"MESSAGES"])
leg=straight_leg_factory(ex,ey,42,68,x,y,39,25,21+2*card_margin,15+2*card_margin)
lines += u"""\n<line x1="%(ex)s" y1="%(ey)s" x2="%(ax)s" y2="%(ay)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'ex': ex, 'ey': ey, 'ax': x, 'ay': y, 'stroke_color': colors['leg_stroke_color']}
(tx,ty)=offset(*leg.card_pos(False,shift[u"RECEVOIR,MESSAGES"]))
lines += u"""\n<text x="%(tx)s" y="%(ty)s" fill="%(text_color)s" font-family="Verdana" font-size="12">1,1</text>""" % {'tx': tx, 'ty': ty, 'text_color': colors['card_text_color']}
lines += u"""\n<g id="association-RECEVOIR">""" % {}
path = upper_round_rect(-39+x,-25+y,78,25,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_cartouche_color'], 'stroke_color': colors['association_cartouche_color']}
path = lower_round_rect(-39+x,0.0+y,78,25,14)
lines += u"""\n	<path d="%(path)s" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'path': path, 'color': colors['association_color'], 'stroke_color': colors['association_color']}
lines += u"""\n	<rect x="%(x)s" y="%(y)s" width="78" height="50" fill="%(color)s" rx="14" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x': -39+x, 'y': -25+y, 'color': colors['transparent_color'], 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -39+x, 'y0': 0+y, 'x1': 39+x, 'y1': 0+y, 'stroke_color': colors['association_stroke_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">RECEVOIR</text>""" % {'x': -32+x, 'y': -7.3+y, 'text_color': colors['association_cartouche_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">lu</text>""" % {'x': -32+x, 'y': 17.8+y, 'text_color': colors['association_attribute_text_color']}
lines += u"""\n</g>""" % {}

lines += u"""\n\n<!-- Entity MESSAGES -->"""
(x,y) = (cx[u"MESSAGES"],cy[u"MESSAGES"])
lines += u"""\n<g id="entity-MESSAGES">""" % {}
lines += u"""\n	<g id="frame-MESSAGES">""" % {}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="84" height="25" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'x': -42+x, 'y': -68+y, 'color': colors['entity_cartouche_color'], 'stroke_color': colors['entity_cartouche_color']}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="84" height="111" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'x': -42+x, 'y': -43.0+y, 'color': colors['entity_color'], 'stroke_color': colors['entity_color']}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="84" height="136" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x': -42+x, 'y': -68+y, 'color': colors['transparent_color'], 'stroke_color': colors['entity_stroke_color']}
lines += u"""\n		<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -42+x, 'y0': -43+y, 'x1': 42+x, 'y1': -43+y, 'stroke_color': colors['entity_stroke_color']}
lines += u"""\n	</g>""" % {}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">MESSAGES</text>""" % {'x': -34+x, 'y': -50.3+y, 'text_color': colors['entity_cartouche_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">idMes</text>""" % {'x': -37+x, 'y': -25.3+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -37+x, 'y0': -23+y, 'x1': -2+x, 'y1': -23+y, 'stroke_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">émetteur</text>""" % {'x': -37+x, 'y': -8.3+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">receveur</text>""" % {'x': -37+x, 'y': 8.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">contenuMes</text>""" % {'x': -37+x, 'y': 25.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">dateMes</text>""" % {'x': -37+x, 'y': 42.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">heureMes</text>""" % {'x': -37+x, 'y': 59.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n</g>""" % {}

lines += u"""\n\n<!-- Entity INVITATION -->"""
(x,y) = (cx[u"INVITATION"],cy[u"INVITATION"])
lines += u"""\n<g id="entity-INVITATION">""" % {}
lines += u"""\n	<g id="frame-INVITATION">""" % {}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="86" height="25" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'x': -43+x, 'y': -68+y, 'color': colors['entity_cartouche_color'], 'stroke_color': colors['entity_cartouche_color']}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="86" height="111" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'x': -43+x, 'y': -43.0+y, 'color': colors['entity_color'], 'stroke_color': colors['entity_color']}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="86" height="136" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x': -43+x, 'y': -68+y, 'color': colors['transparent_color'], 'stroke_color': colors['entity_stroke_color']}
lines += u"""\n		<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -43+x, 'y0': -43+y, 'x1': 43+x, 'y1': -43+y, 'stroke_color': colors['entity_stroke_color']}
lines += u"""\n	</g>""" % {}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">INVITATION</text>""" % {'x': -38+x, 'y': -50.3+y, 'text_color': colors['entity_cartouche_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">idInv</text>""" % {'x': -38+x, 'y': -25.3+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -38+x, 'y0': -23+y, 'x1': -6+x, 'y1': -23+y, 'stroke_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">émetteur</text>""" % {'x': -38+x, 'y': -8.3+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">receveur</text>""" % {'x': -38+x, 'y': 8.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">contenuInv</text>""" % {'x': -38+x, 'y': 25.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">dateInv</text>""" % {'x': -38+x, 'y': 42.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">heureInv</text>""" % {'x': -38+x, 'y': 59.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n</g>""" % {}

lines += u"""\n\n<!-- Entity JOUEUR -->"""
(x,y) = (cx[u"JOUEUR"],cy[u"JOUEUR"])
lines += u"""\n<g id="entity-JOUEUR">""" % {}
lines += u"""\n	<g id="frame-JOUEUR">""" % {}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="70" height="25" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'x': -35+x, 'y': -93+y, 'color': colors['entity_cartouche_color'], 'stroke_color': colors['entity_cartouche_color']}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="70" height="161" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'x': -35+x, 'y': -68.0+y, 'color': colors['entity_color'], 'stroke_color': colors['entity_color']}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="70" height="186" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x': -35+x, 'y': -93+y, 'color': colors['transparent_color'], 'stroke_color': colors['entity_stroke_color']}
lines += u"""\n		<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -35+x, 'y0': -68+y, 'x1': 35+x, 'y1': -68+y, 'stroke_color': colors['entity_stroke_color']}
lines += u"""\n	</g>""" % {}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">JOUEUR</text>""" % {'x': -25+x, 'y': -75.3+y, 'text_color': colors['entity_cartouche_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">pseudo</text>""" % {'x': -30+x, 'y': -50.3+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -30+x, 'y0': -48+y, 'x1': 14+x, 'y1': -48+y, 'stroke_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">e-mail</text>""" % {'x': -30+x, 'y': -33.3+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">login</text>""" % {'x': -30+x, 'y': -16.3+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">mdp</text>""" % {'x': -30+x, 'y': 0.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">avatar</text>""" % {'x': -30+x, 'y': 17.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">statut</text>""" % {'x': -30+x, 'y': 34.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">nb_partie</text>""" % {'x': -30+x, 'y': 51.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">score</text>""" % {'x': -30+x, 'y': 68.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">rôle</text>""" % {'x': -30+x, 'y': 85.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n</g>""" % {}

lines += u"""\n\n<!-- Entity PARTIE -->"""
(x,y) = (cx[u"PARTIE"],cy[u"PARTIE"])
lines += u"""\n<g id="entity-PARTIE">""" % {}
lines += u"""\n	<g id="frame-PARTIE">""" % {}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="82" height="25" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'x': -41+x, 'y': -93+y, 'color': colors['entity_cartouche_color'], 'stroke_color': colors['entity_cartouche_color']}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="82" height="161" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="0"/>""" % {'x': -41+x, 'y': -68.0+y, 'color': colors['entity_color'], 'stroke_color': colors['entity_color']}
lines += u"""\n		<rect x="%(x)s" y="%(y)s" width="82" height="186" fill="%(color)s" stroke="%(stroke_color)s" stroke-width="2"/>""" % {'x': -41+x, 'y': -93+y, 'color': colors['transparent_color'], 'stroke_color': colors['entity_stroke_color']}
lines += u"""\n		<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -41+x, 'y0': -68+y, 'x1': 41+x, 'y1': -68+y, 'stroke_color': colors['entity_stroke_color']}
lines += u"""\n	</g>""" % {}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">PARTIE</text>""" % {'x': -23+x, 'y': -75.3+y, 'text_color': colors['entity_cartouche_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">id</text>""" % {'x': -36+x, 'y': -50.3+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<line x1="%(x0)s" y1="%(y0)s" x2="%(x1)s" y2="%(y1)s" stroke="%(stroke_color)s" stroke-width="1"/>""" % {'x0': -36+x, 'y0': -48+y, 'x1': -24+x, 'y1': -48+y, 'stroke_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">numéro</text>""" % {'x': -36+x, 'y': -33.3+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">texte_JSON</text>""" % {'x': -36+x, 'y': -16.3+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">dateDebut</text>""" % {'x': -36+x, 'y': 0.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">heureDebut</text>""" % {'x': -36+x, 'y': 17.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">statut</text>""" % {'x': -36+x, 'y': 34.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">scoreJ1</text>""" % {'x': -36+x, 'y': 51.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">scoreJ2</text>""" % {'x': -36+x, 'y': 68.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n	<text x="%(x)s" y="%(y)s" fill="%(text_color)s" font-family="Verdana" font-size="12">dureeJeu</text>""" % {'x': -36+x, 'y': 85.8+y, 'text_color': colors['entity_attribute_text_color']}
lines += u"""\n</g>""" % {}
annotation_overlay_height = 40
annotation_baseline = 24
annotation_font = {u'family': u'Verdana', u'size': 14}
annotation_text_color = "#f7f7f7"
annotation_color = "#b35806"
annotation_opacity = 0.9

lines += '\n\n<!-- Annotations -->'
lines += '\n<script type="text/ecmascript">'
lines += '\n<![CDATA['
lines += '\n	function show(evt, text) {'
lines += '\n		var pos = (evt.target.getAttribute("y") < %s) ? "bottom" : "top"' % (height - annotation_overlay_height - card_margin)
lines += '\n		var annotation = document.getElementById(pos + "_annotation_JwhgCbq4")'
lines += '\n		annotation.textContent = text'
lines += '\n		annotation.setAttributeNS(null, "visibility", "visible");'
lines += '\n		document.getElementById(pos + "_overlay_JwhgCbq4").setAttributeNS(null, "visibility", "visible");'
lines += '\n	}'
lines += '\n	function hide(evt) {'
lines += '\n		document.getElementById("top_annotation_JwhgCbq4").setAttributeNS(null, "visibility", "hidden");'
lines += '\n		document.getElementById("top_overlay_JwhgCbq4").setAttributeNS(null, "visibility", "hidden");'
lines += '\n		document.getElementById("bottom_annotation_JwhgCbq4").setAttributeNS(null, "visibility", "hidden");'
lines += '\n		document.getElementById("bottom_overlay_JwhgCbq4").setAttributeNS(null, "visibility", "hidden");'
lines += '\n	}'
lines += '\n]]>'
lines += '\n</script>'
lines += '\n<rect id="top_overlay_JwhgCbq4" x="0" y="0" width="%s" height="%s" fill="%s" stroke-width="0" opacity="%s" visibility="hidden"/>' % (width, annotation_overlay_height, annotation_color, annotation_opacity)
lines += '\n<text id="top_annotation_JwhgCbq4" text-anchor="middle" x="%s" y="%s" fill="%s" font-family="%s" font-size="%s" visibility="hidden"></text>' % (width/2, annotation_baseline, annotation_text_color, annotation_font['family'], annotation_font['size'])
lines += '\n<rect id="bottom_overlay_JwhgCbq4" x="0" y="%s" width="%s" height="%s" fill="%s" stroke-width="0" opacity="%s" visibility="hidden"/>' % (height-annotation_overlay_height, width, annotation_overlay_height, annotation_color, annotation_opacity)
lines += '\n<text id="bottom_annotation_JwhgCbq4" text-anchor="middle" x="%s" y="%s" fill="%s" font-family="%s" font-size="%s" visibility="hidden"></text>' % (width/2, height-annotation_overlay_height+annotation_baseline, annotation_text_color, annotation_font['family'], annotation_font['size'])
lines += u'\n</svg>'

with codecs.open("Joueurs.svg", "w", "utf8") as f:
    f.write(lines)
safe_print_for_PHP(u'Fichier de sortie "Joueurs.svg" généré avec succès.')