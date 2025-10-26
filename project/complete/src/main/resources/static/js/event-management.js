var EventManagementPage = (function(){
  function ensureDayModal(){
    if (document.getElementById('dayEventsModal')) return;
    var wrapper = document.createElement('div');
    wrapper.innerHTML = '<div class="modal fade" id="dayEventsModal" tabindex="-1">\
      <div class="modal-dialog modal-lg modal-dialog-scrollable">\
        <div class="modal-content">\
          <div class="modal-header">\
            <h5 class="modal-title"><i class="fas fa-list me-2"></i>Sự kiện trong ngày</h5>\
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>\
          </div>\
          <div class="modal-body"><div id="day-events-list" class="list-group"></div></div>\
          <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button></div>\
        </div>\
      </div>\
    </div>';
    document.body.appendChild(wrapper.firstChild);
  }
  function getEventsData(){
    if (Array.isArray(window.EVENTS_DATA) && window.EVENTS_DATA.length>0) return window.EVENTS_DATA;
    var arr = [];
    function parseList(id){ var list=document.getElementById(id); if(!list) return; list.querySelectorAll('a[data-id]').forEach(function(a){ var id=a.getAttribute('data-id'); var title=(a.querySelector('.list-title')||{}).textContent||''; var s=a.querySelector('.text-muted span'); var t=s?s.textContent.trim():''; var dt=parseVNDateTime(t); if(id&&dt) arr.push({id:id,title:title.trim(),startTime:dt.toISOString()}); }); }
    parseList('upcoming-list'); parseList('past-list'); return arr;
  }
  function indexEventsByDay(events){ var map={}; events.forEach(function(ev){ var d=new Date(ev.startTime); var y=d.getFullYear(), m=d.getMonth()+1, da=d.getDate(); var key=y+'-'+('0'+m).slice(-2)+'-'+('0'+da).slice(-2); (map[key]=map[key]||[]).push(ev); }); return map; }
  function parseVNDateTime(s){ var m=/^(\d{2})\/(\d{2})\/(\d{4})\s+(\d{2}):(\d{2})$/.exec(s||''); if(!m) return null; return new Date(parseInt(m[3],10), parseInt(m[2],10)-1, parseInt(m[1],10), parseInt(m[4],10), parseInt(m[5],10)); }
  function fmtDateTime(s){ var d=new Date(s); return ('0'+d.getDate()).slice(-2)+'/'+('0'+(d.getMonth()+1)).slice(-2)+'/'+d.getFullYear()+' '+('0'+d.getHours()).slice(-2)+':'+('0'+d.getMinutes()).slice(-2); }
  function fmtTime(s){ var d=new Date(s); return ('0'+d.getHours()).slice(-2)+':'+('0'+d.getMinutes()).slice(-2); }
  function toLocal(s){ var d=new Date(s); var y=d.getFullYear(); var m=('0'+(d.getMonth()+1)).slice(-2); var da=('0'+d.getDate()).slice(-2); var h=('0'+d.getHours()).slice(-2); var mi=('0'+d.getMinutes()).slice(-2); return y+'-'+m+'-'+da+'T'+h+':'+mi; }
  function recurrenceLabel(r){ switch(String(r||'NONE')){ case 'MONTHLY': return 'Hàng tháng'; case 'YEARLY': return 'Hàng năm'; default: return 'Không lặp'; } }
  function fmtLabelMonth(date){ var lbl=document.getElementById('month-label'); var m=date.getMonth()+1, y=date.getFullYear(); if(lbl) lbl.textContent='Tháng '+m+'/'+y; }
  function buildMonthGrid(state){
    var grid=document.getElementById('calendar-grid'); if(!grid){ var cal=document.querySelector('.calendar'); if(!cal) return; grid=document.createElement('div'); grid.id='calendar-grid'; grid.className='grid'; cal.appendChild(grid); }
    grid.innerHTML='';
    ;['CN','T2','T3','T4','T5','T6','T7'].forEach(function(h){ var el=document.createElement('div'); el.className='dow'; el.textContent=h; grid.appendChild(el); });
    var y=state.current.getFullYear(), m=state.current.getMonth(); fmtLabelMonth(state.current);
    var first=new Date(y,m,1), start=first.getDay(), days=new Date(y,m+1,0).getDate();
    for(var i=0;i<start;i++){ grid.appendChild(document.createElement('div')); }
    var byDay=indexEventsByDay(state.events);
    for(var d=1; d<=days; d++){
      var cell=document.createElement('div'); cell.className='cell'; var dateObj=new Date(y,m,d);
      var lunar=(window.VNLunar&&VNLunar.solarToLunar)?VNLunar.solarToLunar(dateObj):null;
      var dateEl=document.createElement('div'); dateEl.className='date'; dateEl.textContent=d;
      var lunarEl=document.createElement('div'); lunarEl.className='lunar'; lunarEl.textContent=lunar?(lunar.day+'/'+lunar.month+(lunar.leap?' (N)':'')):'';
      var mini=document.createElement('div'); mini.className='events-mini'; var key=y+'-'+('0'+(m+1)).slice(-2)+'-'+('0'+d).slice(-2);
      var list=byDay[key]||[]; list.slice(0,2).forEach(function(ev){ var line=document.createElement('div'); line.textContent='• '+ev.title; mini.appendChild(line); }); if(list.length>2){ var more=document.createElement('div'); more.textContent='+'+(list.length-2)+' sự kiện'; mini.appendChild(more); }
      cell.appendChild(dateEl); cell.appendChild(lunarEl); cell.appendChild(mini);
      (function(selKey){ cell.addEventListener('click', function(){ openDayEvents(selKey, byDay); }); })(key);
      grid.appendChild(cell);
    }
  }
  function openDayEvents(key, byDay){ ensureDayModal(); var listDiv=document.getElementById('day-events-list'); if(!listDiv) return; listDiv.innerHTML=''; var list=byDay[key]||[]; if(list.length===0){ listDiv.innerHTML='<div class="text-muted">Không có sự kiện</div>'; } list.forEach(function(ev){ var a=document.createElement('a'); a.href='#'; a.className='list-group-item list-group-item-action'; a.setAttribute('data-id', ev.id); a.innerHTML='<div class="d-flex justify-content-between w-100"><div>'+escapeHtml(ev.title)+'</div><div class="text-muted">'+fmtTime(ev.startTime)+'</div></div>'; a.addEventListener('click', function(e){ e.preventDefault(); loadDetail(ev.id); }); listDiv.appendChild(a); }); var modal=new bootstrap.Modal(document.getElementById('dayEventsModal')); modal.show(); }
  function loadDetail(id){ fetch(window.__EVENTS__.detailUrl+id).then(function(r){ if(!r.ok) throw new Error('Not found'); return r.json(); }).then(showDetail).catch(function(){ alert('Không tải được chi tiết sự kiện'); }); }
  function showDetail(ev){
    var container=document.getElementById('detail-content'); if(!container) return;
    var peopleLine = '';
    var cur = typeof ev.currentParticipants==='number' ? ev.currentParticipants : 0;
    var max = typeof ev.maxParticipants==='number' ? ev.maxParticipants : null;
    if (String(ev.status)==='ONGOING' && max!=null){
      peopleLine = '<div class="mb-2"><i class="fas fa-users me-1"></i>'+cur+'/'+max+' đang tham gia</div>';
    } else if (max!=null){
      peopleLine = '<div class="mb-2"><i class="fas fa-users me-1"></i>Tối đa: '+max+'</div>';
    }
    container.innerHTML=
      '<h5 class="mb-2">'+escapeHtml(ev.title)+'</h5>'+
      '<div class="text-muted mb-2"><i class="far fa-clock me-1"></i>'+fmtDateTime(ev.startTime)+' - '+fmtTime(ev.endTime)+' · <span class="status-'+ev.status+'">'+ev.status+'</span></div>'+
      '<div class="mb-2"><i class="fas fa-repeat me-1"></i>'+recurrenceLabel(ev.recurrence)+'</div>'+
      peopleLine+
      '<div class="mb-2"><i class="fas fa-location-dot me-1"></i>'+escapeHtml(ev.location||'')+'</div>'+
      '<div>'+escapeHtml(ev.description||'')+'</div>';
    var modal=new bootstrap.Modal(document.getElementById('detailModal')); modal.show();
    var editBtn=document.getElementById('editBtn'); var delBtn=document.getElementById('deleteBtn'); if(editBtn) editBtn.onclick=function(){ openEdit(ev); }; if(delBtn) delBtn.onclick=function(){ openDelete(ev); };
  }
  function openEdit(ev){
    var form=document.getElementById('eventForm'); if(!form) return;
    form.action=window.__EVENTS__.updateUrl(ev.id);
    form.querySelector('[name=title]').value=ev.title||'';
    form.querySelector('[name=location]').value=ev.location||'';
    form.querySelector('[name=description]').value=ev.description||'';
    form.querySelector('[name=startTime]').value=toLocal(ev.startTime);
    form.querySelector('[name=endTime]').value=toLocal(ev.endTime);
    var mp=form.querySelector('[name=maxParticipants]'); if(mp) mp.value=ev.maxParticipants||'';
    var cp=form.querySelector('[name=currentParticipants]'); if(cp) cp.value=(typeof ev.currentParticipants==='number'?ev.currentParticipants:'');
    var rec=form.querySelector('[name=recurrence]'); if(rec&&ev.recurrence) rec.value=ev.recurrence;
    var st=form.querySelector('[name=status]'); if(st&&ev.status) st.value=ev.status;
    var modal=new bootstrap.Modal(document.getElementById('createModal')); modal.show();
  }
  function openDelete(ev){ var form=document.getElementById('deleteForm'); if(!form) return; form.action=window.__EVENTS__.deleteUrl(ev.id); var modal=new bootstrap.Modal(document.getElementById('confirmDeleteModal')); modal.show(); }
  function escapeHtml(str){ return String(str).replace(/[&<>\"]/g, function(s){return ({'&':'&amp;','<':'&lt;','>':'&gt;','\"':'&quot;'}[s]);}); }
  function bindNav(state){ var prev=document.getElementById('prev-month'); var next=document.getElementById('next-month'); var today=document.getElementById('today'); function redirectTo(y,m,selected){ var url='/events?year='+y+'&month='+m; if(selected) url+='&date='+selected; window.location.href=url; } if(prev) prev.onclick=function(){ var y=state.current.getFullYear(), m=state.current.getMonth()+1; var d=new Date(y, m-2, 1); redirectTo(d.getFullYear(), d.getMonth()+1); }; if(next) next.onclick=function(){ var y=state.current.getFullYear(), m=state.current.getMonth()+1; var d=new Date(y, m, 1); redirectTo(d.getFullYear(), d.getMonth()+1); }; if(today) today.onclick=function(){ var n=new Date(); redirectTo(n.getFullYear(), n.getMonth()+1); }; var header=document.querySelector('.calendar .header'); if(header && !document.getElementById('pick-date')){ var input=document.createElement('input'); input.type='date'; input.id='pick-date'; input.className='form-control form-control-sm'; input.style.width='160px'; input.style.marginLeft='8px'; header.appendChild(input); } var picker=document.getElementById('pick-date'); if(picker) picker.onchange=function(){ var v=this.value; if(!v) return; var parts=v.split('-'); if(parts.length!==3) return; var y=parseInt(parts[0],10), m=parseInt(parts[1],10); redirectTo(y, m, v); }; }
  function bindLists(){ function handle(e){ var t=e.target.closest('a[data-id]'); if(!t) return; e.preventDefault(); var id=t.getAttribute('data-id'); if(!id) return; loadDetail(id); } var up=document.getElementById('upcoming-list'); var on=document.getElementById('ongoing-list'); var pa=document.getElementById('past-list'); var sr=document.getElementById('search-results'); if(up) up.addEventListener('click', handle); if(on) on.addEventListener('click', handle); if(pa) pa.addEventListener('click', handle); if(sr) sr.addEventListener('click', handle); }
  function maybeRenderSearchResults(){
    if (!window.SEARCH_RESULTS || !window.SEARCH_RESULTS.length) return;
    if (document.getElementById('search-results')) return;
    var leftCol=document.querySelector('.row.g-3 > .col-lg-7'); if(!leftCol) return;
    var col=document.createElement('div'); col.className='col-lg-7';
    var card=document.createElement('div'); card.className='card mt-2';
    card.innerHTML='<div class="card-body"><h5 class="section-title mb-2"><i class="fas fa-search me-2"></i>Kết quả tìm kiếm</h5><div id="search-results" class="list-group"></div></div>';
    col.appendChild(card);
    leftCol.parentNode.insertBefore(col, leftCol.nextSibling);
    var list=document.getElementById('search-results');
    window.SEARCH_RESULTS.forEach(function(e){ var a=document.createElement('a'); a.href='#'; a.className='list-group-item list-group-item-action'; a.setAttribute('data-id', e.id); a.innerHTML='<div><div class="list-title">'+escapeHtml(e.title||'')+'</div><div class="text-muted"><i class="far fa-clock me-1"></i>'+fmtDateTime(e.startTime)+' - '+fmtTime(e.endTime)+'</div></div><i class="fas fa-angle-right text-muted"></i>'; list.appendChild(a); });
  }
  return { init: function(){ var state={ current: new Date((window.CURRENT_YEAR||new Date().getFullYear()), ((window.CURRENT_MONTH||new Date().getMonth()+1)-1), 1), events: getEventsData() }; ensureDayModal(); maybeRenderSearchResults(); bindNav(state); bindLists(); buildMonthGrid(state); } };
})();
