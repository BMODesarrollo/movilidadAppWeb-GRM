/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

PrimeFaces.widget.DataTable.prototype.setupStickyHeader = function () {
    var table = this.thead.parent(),
            offset = table.offset(),
            win = $(window),
            $this = this,
            stickyNS = 'scroll.' + this.id,
            resizeNS = 'resize.sticky-' + this.id,
            layoutHeaderHeight = $('#layout-header').height();  // added the height of layout header.

    this.cloneContainer = $('<div class="ui-datatable ui-datatable-sticky ui-widget"><table></table></div>');
    this.clone = this.thead.clone(true);
    this.cloneContainer.children('table').append(this.clone);

    this.cloneContainer.css({
        position: 'absolute',
        width: table.outerWidth(),
        top: offset.top,
        left: offset.left,
        'z-index': ++PrimeFaces.zindex
    })
            .appendTo(this.jq);

    win.off(stickyNS).on(stickyNS, function () {
        var scrollTop = win.scrollTop(),
                tableOffset = table.offset();

        if (scrollTop > tableOffset.top) {
            $this.cloneContainer.css('top', scrollTop + layoutHeaderHeight)
                    .addClass('ui-shadow ui-sticky');

            if (scrollTop >= (tableOffset.top + $this.tbody.height())) {
                $this.cloneContainer.hide();
            }
            else {

                $this.cloneContainer.show();
            }
        }
        else {
            $this.cloneContainer.css('top', tableOffset.top)
                    .removeClass('ui-shadow ui-sticky');
        }
    })
            .off(resizeNS).on(resizeNS, function () {
        $this.cloneContainer.width(table.outerWidth());
    });

    //filter support
    this.thead.find('.ui-column-filter').prop('disabled', true);
};


