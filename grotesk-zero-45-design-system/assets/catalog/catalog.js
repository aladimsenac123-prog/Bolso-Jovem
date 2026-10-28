/* ================================================================ catalog routing
   Overview | Components. Overview is the live page in a borderless frame; Components parks that
   frame on its hero stage (bone, registration cross, dissolving photograph, grain) as the live background. */
(function () {
  "use strict";
  var COMPONENTS_ENABLED = true;   /* Overview approved (orchestrated run) */
  var body = document.body, nav = document.getElementById('ds-nav'), frame = document.getElementById('overview-frame');
  var linkO = document.getElementById('nav-overview'), linkC = document.getElementById('nav-components');
  var main = document.getElementById('ds-main'), side = document.getElementById('ds-side');
  var isComp = function (h) { return h === '#components' || h.indexOf('#components-') === 0; };
  var mode = 'overview';
  if (!COMPONENTS_ENABLED) linkC.setAttribute('aria-disabled', 'true');

  function send(m) { try { frame.contentWindow.postMessage(m, '*'); } catch (e) {} }
  function setNavH() { document.documentElement.style.setProperty('--ds-navh', nav.offsetHeight + 'px'); }
  addEventListener('resize', setNavH, { passive: true });
  setNavH();

  function post() { send({ ds: mode === 'components' ? 'bg' : 'page' }); }
  function route() {
    var h = location.hash;
    if (isComp(h) && !COMPONENTS_ENABLED) { history.replaceState(null, '', location.pathname + location.search); h = ''; }
    mode = isComp(h) ? 'components' : 'overview';
    body.classList.toggle('is-components', mode === 'components');
    linkO.classList.toggle('is-active', mode === 'overview');
    linkC.classList.toggle('is-active', mode === 'components');
    setNavH();
    post();
    window.dispatchEvent(new CustomEvent('ds:mode', { detail: mode }));
    if (mode !== 'components') { body.classList.remove('ds-scene-clear'); return; }
    if (h === '#components') { main.scrollTop = 0; spy(); return; }
    var el = document.getElementById(h.slice(1));
    if (el) main.scrollTop = el.offsetTop - 24;
    spy();
  }
  addEventListener('message', function (e) {
    if (e.source !== frame.contentWindow || !e.data) return;
    if (e.data.ds === 'ready') body.classList.add('ds-overview-ready');
  });
  frame.addEventListener('load', post);

  /* sidebar follows the scroll; the scrim lifts over Backgrounds */
  var groups = [].slice.call(main.querySelectorAll('.ds-group'));
  var sideLinks = [].slice.call(side.querySelectorAll('a'));
  function spy() {
    if (!groups.length) return;
    var y = main.scrollTop + 160, cur = groups[0];
    groups.forEach(function (g) { if (g.offsetTop <= y) cur = g; });
    sideLinks.forEach(function (a) {
      var on = a.getAttribute('href') === '#' + cur.id;
      if (on && !a.classList.contains('is-current') && innerWidth < 768) side.scrollLeft = a.offsetLeft - 16;
      a.classList.toggle('is-current', on);
    });
    body.classList.toggle('ds-scene-clear', cur.id === 'components-backgrounds');
  }
  main.addEventListener('scroll', spy, { passive: true });

  /* sidebar links scroll the view without leaving a history entry per group */
  side.addEventListener('click', function (e) {
    var a = e.target.closest('a[href^="#components-"]');
    if (!a) return;
    e.preventDefault();
    history.replaceState(null, '', a.getAttribute('href'));
    route();
  });

  /* specimens stay put: links and form submits never navigate */
  main.addEventListener('click', function (e) { if (e.target.closest('a')) e.preventDefault(); }, true);
  main.addEventListener('submit', function (e) { e.preventDefault(); }, true);

  addEventListener('hashchange', route);
  route();
})();

/* ================================================================ specimen behaviour
   The page's own GSAP code, scoped to each specimen. ScrollTrigger reads this view's scroller
   (#ds-main) instead of the window; tickers idle while Overview is showing. */
(function () {
  "use strict";
  if (!window.gsap || !window.ScrollTrigger) return;
  gsap.registerPlugin(ScrollTrigger);
  var main = document.getElementById('ds-main');
  ScrollTrigger.defaults({ scroller: main });
  var $ = function (s, r) { return (r || document).querySelector(s); };
  var $$ = function (s, r) { return [].slice.call((r || document).querySelectorAll(s)); };
  var reduce = matchMedia('(prefers-reduced-motion: reduce)').matches;
  var touch = matchMedia('(pointer: coarse)').matches;
  var live = function () { return document.body.classList.contains('is-components'); };
  var clamp = function (v) { return Math.min(Math.max(v, 0), 1); };
  var replays = {};

  /* ---------- ribbons: infinite + scroll-velocity coupling ---------- */
  var rib = $('[data-ds-ribbons]');
  if (rib && !reduce) {
    var r1 = { x: 0 }, r2 = { x: -50 }, vel = 0, e1 = $('.r1', rib), e2 = $('.r2', rib);
    ScrollTrigger.create({ onUpdate: function (self) { vel = self.getVelocity() / 4000; } });
    gsap.ticker.add(function (t, dt) {
      if (!live()) return;
      var base = dt * 0.0022;
      r1.x -= base * (1 + Math.min(Math.abs(vel), 3)); if (r1.x <= -50) r1.x += 50;
      r2.x += base * (1 + Math.min(Math.abs(vel), 3)); if (r2.x >= 0) r2.x -= 50;
      e1.style.transform = 'translate3d(' + r1.x + '%,0,0)';
      e2.style.transform = 'translate3d(' + r2.x + '%,0,0)';
    });
  }

  /* ---------- hero: per-glyph weight under the pointer + intro replay ---------- */
  var hb = $('[data-ds-hero]');
  if (hb) {
    var hero = $('.hero', hb), chs = $$('.hero-title .ch', hb);
    if (!touch && !reduce && chs.length) {
      var hx = -9999, hy = -9999, heroOn = false;
      hero.addEventListener('pointermove', function (e) { hx = e.clientX; hy = e.clientY; heroOn = true; });
      hero.addEventListener('pointerleave', function () { heroOn = false; });
      var states = chs.map(function (ch) { var b = ch.closest('.row').classList.contains('thin') ? 100 : 900; return { ch: ch, w: b, base: b }; });
      gsap.ticker.add(function () {
        if (!live()) return;
        states.forEach(function (s) {
          var target = s.base;
          if (heroOn) {
            var r = s.ch.getBoundingClientRect();
            var f = Math.max(0, 1 - Math.hypot(hx - (r.left + r.width / 2), hy - (r.top + r.height / 2)) / 340);
            target = s.base === 900 ? 900 - 800 * f : 100 + 800 * f;
          }
          s.w += (target - s.w) * 0.16;
          s.ch.style.fontVariationSettings = "'wght' " + s.w.toFixed(0) + ", 'wdth' 100";
        });
      });
    }
    replays.hero = function () {
      var veil = $('.veil', hb), rows = $$('.hero-title .row > span', hb);
      gsap.set(veil, { display: 'flex', yPercent: 0 });
      gsap.timeline({ delay: 0.25 })
        .to(veil, { yPercent: -100, duration: 0.9, ease: 'power3.inOut' })
        .set(veil, { display: 'none' })
        .from($('.reg-v', hb), { scaleY: 0, duration: 1.0, ease: 'expo.out' }, '-=0.45')
        .from($('.reg-h', hb), { scaleX: 0, duration: 1.0, ease: 'expo.out' }, '<')
        .fromTo($('.reg-cross', hb), { opacity: 0 }, { opacity: 0.5, duration: 0.6 }, '<0.3')
        .fromTo(rows, { yPercent: 115 }, { yPercent: 0, duration: 1.1, ease: 'expo.out', stagger: 0.12 }, '-=0.7')
        .fromTo($('.hero-sub', hb), { opacity: 0, y: 20 }, { opacity: 1, y: 0, duration: 0.8, ease: 'expo.out' }, '-=0.5')
        .fromTo($('.hero-bed', hb), { opacity: 0 }, { opacity: 1, duration: 1.4, ease: 'power2.out' }, '-=0.9');
    };
  }

  /* ---------- manifesto: words ink in (looping progress) + drifting shards ---------- */
  var mani = $('[data-ds-mani]');
  if (mani) {
    var words = $$('.w', mani), mp = { p: 0 };
    var paint = function () {
      var k = clamp((mp.p - 0.05) / 0.75), n = Math.floor(k * words.length);
      words.forEach(function (w, i) { w.classList.toggle('on', i < n); });
    };
    if (reduce) { mp.p = 1; paint(); }
    else {
      var mloop = gsap.to(mp, { p: 1, duration: 7, ease: 'none', repeat: -1, repeatDelay: 1.4, paused: true, onUpdate: paint });
      ScrollTrigger.create({ trigger: mani, start: 'top bottom', end: 'bottom top', onToggle: function (self) { if (self.isActive) mloop.play(); else mloop.pause(); } });
    }
  }
  if (!reduce) $$('[data-ds-mani], [data-ds-drift]').forEach(function (box) {
    $$('.mani-shard', box).forEach(function (sh, i) {
      gsap.fromTo(sh, { yPercent: i ? -70 : 80 }, { yPercent: i ? 80 : -100, ease: 'none',
        scrollTrigger: { trigger: box, start: 'top bottom', end: 'bottom top', scrub: true } });
    });
  });

  /* ---------- photograph parallax + knockout ---------- */
  if (!reduce) $$('[data-ds-parallax], [data-ds-inter]').forEach(function (sec) {
    var st = { trigger: sec, start: 'top bottom', end: 'bottom top', scrub: true };
    gsap.fromTo($('img', sec), { yPercent: -8 }, { yPercent: 8, ease: 'none', scrollTrigger: st });
    var spans = $$('.knock .fill span', sec);
    if (!spans.length) return;
    gsap.fromTo(spans[0], { backgroundPositionY: '36%' }, { backgroundPositionY: '48%', ease: 'none', scrollTrigger: Object.assign({}, st) });
    gsap.fromTo(spans[1], { backgroundPositionY: '48%' }, { backgroundPositionY: '60%', ease: 'none', scrollTrigger: Object.assign({}, st) });
    gsap.from(spans, { yPercent: 70, opacity: 0, stagger: 0.16, duration: 1.5, ease: 'expo.out', scrollTrigger: { trigger: sec, start: 'top 78%' } });
  });

  /* ---------- footer: ghost zero drifts up, wordmark weight follows the pointer ---------- */
  if (!reduce) $$('[data-ds-footzero], [data-ds-footer]').forEach(function (box) {
    gsap.fromTo($('.foot-zero', box), { yPercent: 16 }, { yPercent: -6, ease: 'none',
      scrollTrigger: { trigger: box, start: 'top bottom', end: 'bottom bottom', scrub: true } });
  });
  if (!touch && !reduce) $$('[data-ds-footer], [data-ds-footmark]').forEach(function (box) {
    var fchs = $$('.fch', box);
    box.addEventListener('pointermove', function (e) {
      var r = box.getBoundingClientRect(), t = (e.clientX - r.left) / r.width;
      fchs.forEach(function (ch, i) {
        var d = Math.abs(i / (fchs.length - 1) - t), w = 900 - Math.min(d * 2.2, 1) * 750;
        ch.style.fontVariationSettings = "'wght' " + w.toFixed(0);
      });
    });
  });

  /* ---------- glyph grid pops in ---------- */
  var gg = $('[data-ds-glyphs]');
  if (gg && !reduce) {
    gg.classList.add('ds-armed');
    ScrollTrigger.create({ trigger: gg, start: 'top 78%', once: true, onEnter: function () {
      var cells = $$('.glyph', gg);
      gsap.set(cells, { opacity: 0, scale: 0.8 });
      gg.classList.remove('ds-armed');
      gsap.to(cells, { opacity: 1, scale: 1, duration: 0.5, ease: 'back.out(1.5)', stagger: { each: 0.018, grid: 'auto', from: 'random' } });
    } });
  }

  /* ---------- reveal ---------- */
  var rv = $('[data-ds-reveal]');
  if (rv) {
    var revEls = $$('.reveal', rv);
    if (!reduce) ScrollTrigger.batch(revEls, { start: 'top 88%', onEnter: function (els) {
      gsap.from(els, { y: 30, autoAlpha: 0, duration: 0.7, ease: 'expo.out', stagger: 0.08 }); } });
    replays.reveal = function () { gsap.fromTo(revEls, { y: 30, autoAlpha: 0 }, { y: 0, autoAlpha: 1, duration: 0.7, ease: 'expo.out', stagger: 0.08 }); };
  }

  /* ---------- features: hover demos ---------- */
  var feats = $('[data-ds-features]');
  if (feats) {
    var anims = {
      count: function (el) { var o = { n: 0 };
        gsap.to(o, { n: 1000000, duration: 0.9, ease: 'power2.out', onUpdate: function () {
          el.textContent = '$' + o.n.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 }); } }); },
      case: function (el) { gsap.fromTo(el, { letterSpacing: '0.2em', opacity: 0.4 }, { letterSpacing: '0em', opacity: 1, duration: 0.6, ease: 'expo.out' }); },
      alt: function (el) { gsap.fromTo(el, { fontVariationSettings: "'wght' 100" }, { fontVariationSettings: "'wght' 800", duration: 0.7, ease: 'expo.out' }); },
      arrows: function (el) { gsap.fromTo(el, { x: -16, opacity: 0.3 }, { x: 0, opacity: 1, duration: 0.5, ease: 'back.out(1.6)' }); }
    };
    $$('.feat-row', feats).forEach(function (row) {
      var demo = $('.demo', row), kind = demo.dataset.anim, orig = demo.innerHTML;
      row.addEventListener('pointerenter', function () { if (!reduce && anims[kind]) anims[kind](demo); });
      row.addEventListener('pointerleave', function () { if (kind === 'count') demo.innerHTML = orig; });
    });
  }

  /* ---------- type tester (the page's real component) ---------- */
  if ($('[data-ds-tester]')) {
    var line = $('#testerLine'), read = $('#testerRead');
    var cW = $('#cWght'), cD = $('#cWdth'), cS = $('#cSize'), rW = $('#rWght'), rD = $('#rWdth'), rS = $('#rSize');
    var state = { w: 700, d: 100, s: 96 };
    var presets = $$('#presets button');
    var clearPreset = function () { presets.forEach(function (b) { b.classList.remove('on'); }); };
    var render = function () {
      line.style.fontVariationSettings = "'wght' " + state.w + ", 'wdth' " + state.d;
      line.style.fontSize = state.s + 'px';
      rW.textContent = state.w; rD.textContent = state.d; rS.textContent = state.s;
      read.innerHTML = 'wght ' + state.w + ' &middot; wdth ' + state.d + ' &middot; ' + state.s + 'px';
    };
    cW.addEventListener('input', function () { state.w = +cW.value; render(); clearPreset(); });
    cD.addEventListener('input', function () { state.d = +cD.value; render(); clearPreset(); });
    cS.addEventListener('input', function () { state.s = +cS.value; render(); });
    presets.forEach(function (b) { b.addEventListener('click', function () {
      clearPreset(); b.classList.add('on');
      gsap.to(state, { w: +b.dataset.w, d: +b.dataset.d, duration: 0.6, ease: 'expo.out', onUpdate: function () {
        state.w = Math.round(state.w); state.d = Math.round(state.d); cW.value = state.w; cD.value = state.d; render(); } });
    }); });
    line.addEventListener('paste', function (e) { e.preventDefault();
      document.execCommand('insertText', false, (e.clipboardData || window.clipboardData).getData('text/plain')); });
    render();
  }
  $$('[data-ds-presets]').forEach(function (g) {
    var bs = $$('button', g);
    bs.forEach(function (b) { b.addEventListener('click', function () { bs.forEach(function (x) { x.classList.remove('on'); }); b.classList.add('on'); }); });
  });
  $$('[data-ds-range]').forEach(function (c) {
    var inp = $('input', c), out = $('label b', c);
    inp.addEventListener('input', function () { out.textContent = inp.value; });
  });

  /* ---------- veil lift ---------- */
  var vb = $('[data-ds-veil]');
  if (vb) replays.veil = function () {
    var v = $('.veil', vb);
    gsap.killTweensOf(v);
    gsap.set(v, { display: 'flex', yPercent: 0 });
    gsap.timeline({ delay: 0.25 }).to(v, { yPercent: -100, duration: 0.9, ease: 'power3.inOut' })
      .set(v, { display: 'none' }).set(v, { display: 'flex', yPercent: 0 }, '+=1.4');
  };

  /* ---------- custom cursor, inside its box ---------- */
  var cb = $('[data-ds-cursor]');
  if (cb) {
    var cur = $('.cursor', cb);
    var cx = cb.clientWidth / 2, cy = cb.clientHeight / 2, tx = cx, ty = cy;
    cur.style.transform = 'translate(' + cx + 'px,' + cy + 'px) translate(-50%,-50%)';
    if (!touch && !reduce) {
      cb.addEventListener('pointermove', function (e) { var r = cb.getBoundingClientRect(); tx = e.clientX - r.left; ty = e.clientY - r.top; });
      gsap.ticker.add(function () {
        if (!live()) return;
        cx += (tx - cx) * 0.22; cy += (ty - cy) * 0.22;
        cur.style.transform = 'translate(' + cx + 'px,' + cy + 'px) translate(-50%,-50%)';
      });
      $$('a,button,.lic-row,.glyph,input,[contenteditable]', cb).forEach(function (el) {
        el.addEventListener('pointerenter', function () { cur.classList.add('is-link'); });
        el.addEventListener('pointerleave', function () { cur.classList.remove('is-link'); });
      });
    }
  }

  /* ---------- scroll scenes: the page's update functions, driven by a loop or the scrubber ---------- */
  function scene(root, update, start) {
    var drv = { p: start || 0 }, input = $('[data-ds-scrub]', root.parentNode), out = $('[data-ds-read]', root.parentNode), held = false;
    var sync = function () { input.value = Math.round(drv.p * 1000); out.textContent = drv.p.toFixed(2); };
    var step = function () { update(drv.p); sync(); };
    var loop = gsap.fromTo(drv, { p: 0 }, { p: 1, duration: 6, ease: 'none', repeat: -1, yoyo: true, repeatDelay: 0.8, paused: true, onUpdate: step });
    loop.progress(start || 0).pause();
    input.addEventListener('input', function () { held = true; loop.pause(); drv.p = input.value / 1000; step(); });
    if (!reduce) ScrollTrigger.create({ trigger: root, start: 'top bottom', end: 'bottom top',
      onToggle: function (self) { if (self.isActive && !held) loop.play(); else loop.pause(); } });
    addEventListener('resize', step, { passive: true });
    addEventListener('ds:mode', step);
    step();
  }

  var portal = $('[data-ds-portal]');
  if (portal) {
    var pStage = $('.stage', portal), oWrap = $('.o-wrap', pStage), pLine = $('.portal-line', pStage),
        oGlyph = $('.o-glyph', pStage), shards = $$('.shard', pStage), eyebrow = $('.portal-eyebrow', pStage), FLIP = 0.69;
    scene(portal, function (p) {
      var bw = pStage.clientWidth || 1, bh = pStage.clientHeight || 1, vmin = Math.min(bw, bh) / 100;
      var travel = clamp((p - 0.15) / 0.597);
      if (p < FLIP + 0.03) {
        var fs = 64 * Math.pow(46, travel);
        var dx = 36 * gsap.parseEase('power2.inOut')(clamp((travel - 0.1) / 0.7));
        oGlyph.style.fontSize = (fs * vmin).toFixed(2) + 'px';
        oWrap.style.transform = 'translate3d(' + dx.toFixed(2) + '%,0,0)';
      }
      eyebrow.style.opacity = 1 - clamp((p - 0.42) / 0.16);
      pStage.classList.toggle('inked', p >= FLIP);
      var ip = clamp((p - 0.40) / 0.60), lk = clamp((p - 0.66) / 0.10);
      pLine.style.opacity = lk;
      pLine.style.transform = 'translate(-50%,-50%) translateY(' + ((1 - lk) * 22) + 'px)';
      shards.forEach(function (sh, i) {
        var near = sh.classList.contains('s-near'), mid = sh.classList.contains('s-mid');
        var depth = near ? 190 : mid ? 130 : 85, drift = (i % 2 ? 1 : -1) * ip * 7;
        sh.style.fontSize = ((near ? 26 : mid ? 16 : 9) * vmin).toFixed(1) + 'px';
        sh.style.transform = 'translate3d(' + (drift * bw / 100).toFixed(2) + 'px,' + (-ip * depth * bh / 100).toFixed(2) + 'px,0)';
      });
      pStage.style.backgroundColor = gsap.utils.interpolate('#f4f1ea', '#161513')(clamp((p - 0.613) / 0.077));
      oWrap.style.opacity = p >= FLIP ? 0 : 1;
    }, 0);
  }

  var axis = $('[data-ds-axis]');
  if (axis) {
    var NAMES = ['hairline', 'thin', 'light', 'book', 'regular', 'medium', 'nerve', 'bold', 'black'];
    var word = $('#zeroWord'), hud = $('.axis-hud', axis), tick = $('#railTick'), num = $('#wghtNum'), nm = $('#wghtName'), rail = $('.axis-rail', axis);
    for (var i = 0; i < 9; i++) { var n = document.createElement('i'); n.style.left = (i / 8 * 100) + '%'; rail.appendChild(n); }
    var detent = -1;
    var apply = function (idx) {
      if (idx === detent) return; detent = idx;
      var w = 100 + idx * 100;
      word.style.fontVariationSettings = "'wght' " + w;
      num.textContent = 'wght ' + w; nm.textContent = NAMES[idx];
      nm.classList.remove('stamped'); void nm.offsetWidth; nm.classList.add('stamped');
    };
    scene(axis, function (p) {
      var rev = gsap.parseEase('expo.out')(clamp((p - 0.08) / 0.34));
      word.style.transform = 'translateY(' + ((1 - rev) * 115) + '%)';
      hud.style.opacity = clamp((p - 0.30) / 0.12);
      var wp = clamp((p - 0.44) / 0.52);
      apply(Math.round(wp * 8));
      tick.style.left = (wp * 100) + '%';
    }, 0.62);
  }

  /* ---------- in use: the track slides with this view's scroll ---------- */
  var tr = $('[data-ds-track]');
  if (tr && !reduce) {
    var track = $('.track', tr), tbox = tr.closest('.ds-trackbox');
    var dist = function () { return Math.max(0, track.scrollWidth - tbox.clientWidth); };
    var move = gsap.to(track, { x: function () { return -dist(); }, ease: 'none',
      scrollTrigger: { trigger: tbox, start: 'top 85%', end: 'bottom 15%', scrub: 0.6, invalidateOnRefresh: true } });
    $$('.frame', track).forEach(function (f) {
      gsap.fromTo(f, { clipPath: 'inset(0 0 0 28%)' }, { clipPath: 'inset(0 0 0 0%)', duration: 1, ease: 'expo.out',
        scrollTrigger: { trigger: f, containerAnimation: move, start: 'left 90%' } });
      gsap.fromTo($('img', f), { xPercent: -8 }, { xPercent: 0, ease: 'none',
        scrollTrigger: { trigger: f, containerAnimation: move, start: 'left right', end: 'right left', scrub: true } });
    });
  }

  /* ---------- replay buttons + refresh when the view opens ---------- */
  $$('[data-ds-replay]').forEach(function (b) { b.addEventListener('click', function () { var f = replays[b.dataset.dsReplay]; if (f) f(); }); });
  var refresh = function () { requestAnimationFrame(function () { ScrollTrigger.refresh(); }); };
  addEventListener('ds:mode', function (e) { if (e.detail === 'components') refresh(); });
  addEventListener('load', function () { if (live()) refresh(); });
  if (live()) refresh();
})();
